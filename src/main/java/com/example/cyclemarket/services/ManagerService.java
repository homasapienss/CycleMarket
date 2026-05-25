package com.example.cyclemarket.services;

import com.example.cyclemarket.dto.ProductStock;
import com.example.cyclemarket.dto.order.OrderDetailsView;
import com.example.cyclemarket.entities.*;
import com.example.cyclemarket.exception.ApplicationException;
import com.example.cyclemarket.repos.ProductRepo;
import com.example.cyclemarket.repos.StockRepo;
import com.example.cyclemarket.services.entity.EmployeeService;
import com.example.cyclemarket.services.entity.OrderService;
import com.example.cyclemarket.services.entity.ProductService;
import com.example.cyclemarket.services.entity.ShopService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ManagerService {
    private final EmployeeService employeeService;
    private final StockRepo stockRepo;
    private final ProductRepo productRepo;
    private final StockService stockService;
    private final OrderService orderService;
    private final ShopService shopService;
    private final ProductService productService;

    @Transactional(readOnly = true)
    public List<ProductStock> getStockByShop(Authentication authentication, Long shopId) {
        Long accessibleShopId = resolveAccessibleShopId(authentication, shopId);

        List<Stock> stocks = accessibleShopId == null
                ? stockService.getStockAllShops()
                : stockService.getStockByShopId(accessibleShopId);

        return stocks.stream()
                .map(this::toProductStock)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Product> getMissingProductsByShop(Authentication authentication, Long shopId) {
        Long accessibleShopId = resolveAccessibleShopId(authentication, shopId);
        return productRepo.findProductsNotInShopStock(accessibleShopId);
    }

    @Transactional
    public void addToStock(Long productId, Long shopId, Integer quantity, Authentication auth) {
        Long targetShopId = resolveAccessibleShopId(auth, shopId);
        if (stockRepo.existsByShopIdAndProductId(targetShopId, productId)) {
            throw new ApplicationException("Продукт уже добавлен на склад");
        }
        if (targetShopId == null) {
            throw new ApplicationException("Выберите магазин");
        }
        Product product = productService.getById(productId);
        Shop shop = shopService.getById(targetShopId);

        stockRepo.save(Stock.builder()
                .product(product)
                .shop(shop)
                .quantity(quantity)
                .build());
    }

    @Transactional
    public void changeStockQuantity(Authentication authentication, Long productId, Long shopId, Integer delta) {
        Stock stock = getAccessibleStock(authentication, productId, shopId);
        updateStockQuantity(stock, stock.getQuantity() + delta);
    }

    @Transactional
    public void editStockQuantity(Authentication authentication, Long productId, Long shopId, Integer newQuantity) {
        Stock stock = getAccessibleStock(authentication, productId, shopId);
        updateStockQuantity(stock, newQuantity);
    }

    private Stock getAccessibleStock(Authentication authentication, Long productId, Long shopId) {
        Long accessibleShopId = resolveAccessibleShopId(authentication, shopId);
        if (accessibleShopId == null) {
            throw new ApplicationException("Выберите магазин");
        }

        return stockService.getStockByShopAndProduct(accessibleShopId, productId);
    }

    private void updateStockQuantity(Stock stock, Integer newQuantity) {
        if (newQuantity < 0) {
            throw new ApplicationException("Нельзя сделать кол-во товара отрицательным");
        }
        stock.setQuantity(newQuantity);
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersForScope(Authentication authentication, Long shopId, String status) {
        Long accessibleShopId = resolveAccessibleShopId(authentication, shopId);

        if (accessibleShopId != null) {
            return hasStatus(status)
                    ? orderService.getOrdersByShopIdAndStatus(accessibleShopId, status)
                    : orderService.getOrdersByShop(accessibleShopId);
        }

        return hasStatus(status)
                ? orderService.getOrdersByStatus(status)
                : orderService.getOrders();
    }

    @Transactional(readOnly = true)
    public OrderDetailsView getOrderViewForStaff(Long orderId, Authentication authentication) {
        if (isAdmin(authentication)) return orderService.getOrderDetailsForStaff(orderId);

        Long managerShopId = employeeService.getShopIdByEmployeeName(authentication.getName());
        Order orderByIdAndShopId = orderService.getOrderByIdAndShopId(orderId, managerShopId);

        return orderService.getOrderDetailsByOrder(orderByIdAndShopId);
    }

    @Transactional
    public void setOrderStatus(Long orderId, OrderStatus status, Authentication authentication) {
        Order order;

        if (isAdmin(authentication)) {
            order = orderService.getOrder(orderId);
        } else {
            Long managerShopId = employeeService.getShopIdByEmployeeName(authentication.getName());
            order = orderService.getOrderByIdAndShopId(orderId, managerShopId);
        }

        orderService.changeOrderStatus(order, status);
    }

    private boolean hasStatus(String status) {
        return status != null && !status.isBlank();
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    private ProductStock toProductStock(Stock stock) {
        Product product = stock.getProduct();
        Shop shop = stock.getShop();

        return ProductStock.builder()
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .quantity(stock.getQuantity())
                .shopName(shop.getShopName())
                .productId(product.getId())
                .shopId(shop.getId())
                .id(stock.getId())
                .build();
    }


    private Long resolveAccessibleShopId(Authentication authentication, Long shopId) {
        if (isAdmin(authentication)) {
            return shopId;
        }

        Long managerShopId = employeeService.getShopIdByEmployeeName(authentication.getName());

        if (shopId != null && !shopId.equals(managerShopId)) {
            throw new ApplicationException("не твой магазин");
        }

        return managerShopId;
    }


}
