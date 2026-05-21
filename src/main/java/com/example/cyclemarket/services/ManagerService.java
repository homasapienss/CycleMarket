package com.example.cyclemarket.services;

import com.example.cyclemarket.dto.ProductStock;
import com.example.cyclemarket.entities.Product;
import com.example.cyclemarket.entities.Shop;
import com.example.cyclemarket.entities.Stock;
import com.example.cyclemarket.exception.notfound.ProductNotFoundException;
import com.example.cyclemarket.repos.ProductRepo;
import com.example.cyclemarket.repos.StockRepo;
import com.example.cyclemarket.services.entity.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ManagerService {
    private final EmployeeService employeeService;
    private final StockRepo stockRepo;
    private final ProductRepo productRepo;
    private final StockService stockService;

    @Transactional
    public List<ProductStock> getStockByShop(Authentication authentication, Long shopId) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            return shopId != null ?
                    stockService.getProductStockByShop(shopId)
                            .entrySet()
                            .stream()
                            .map(entry -> {
                                Product product = productRepo.findById(entry.getKey()).orElseThrow(ProductNotFoundException::new);
                                return ProductStock.builder()
                                        .id(product.getId())
                                        .productName(product.getProductName())
                                        .productPrice(product.getProductPrice())
                                        .quantity(entry.getValue())
                                        .build();

                            }).toList()
                    :
                    stockService.getStockAllShops()
                            .stream()
                            .map(stock -> {
                                Product product = stock.getProduct();
                                return ProductStock.builder()
                                        .id(product.getId())
                                        .productName(product.getProductName())
                                        .productPrice(product.getProductPrice())
                                        .quantity(stock.getQuantity())
                                        .build();
                            })
                            .toList();
        } else {
            String managerName = authentication.getName();
            Long managerShop = employeeService.getShopIdByEmployeeName(managerName);
            if (shopId != null) {
                if (shopId.equals(managerShop)) {
                    return stockService.getStockByShopId(managerShop)
                            .stream()
                            .map(stock -> {
                                Product product = stock.getProduct();
                                return ProductStock.builder()
                                        .id(product.getId())
                                        .productName(product.getProductName())
                                        .productPrice(product.getProductPrice())
                                        .quantity(stock.getQuantity())
                                        .build();
                            })
                            .toList();
                }else throw new RuntimeException("не твой магазин");
            } else {
                Map<Long, Integer> productStockByShop = stockService.getProductStockByShop(managerShop);
                return productStockByShop.entrySet()
                        .stream()
                        .map(entry -> {
                            Product product = productRepo.findById(entry.getKey()).orElseThrow(ProductNotFoundException::new);
                            return ProductStock.builder()
                                    .id(product.getId())
                                    .productName(product.getProductName())
                                    .productPrice(product.getProductPrice())
                                    .quantity(entry.getValue())
                                    .build();

                        }).toList();
            }
        }
    }

    @Transactional(readOnly = true)
    public List<Product> getNotStockByShop(String name) {
        Long shopId = employeeService.getShopIdByEmployeeName(name);
        return productRepo.findProductsNotInShopStock(shopId);
    }

    @Transactional
    public void addToStock(String name, Long productId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Кол-во не может быть отрицательным");
        }
        Shop shop = employeeService.getCurrentManagerShop(name);
        Product product = productRepo.findById(productId).orElseThrow(ProductNotFoundException::new);

        if (stockRepo.existsByShopIdAndProductId(shop.getId(), productId)) {
            throw new IllegalStateException("Продукт уже добавлен на склад");
        }
        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setQuantity(quantity);
        stock.setShop(shop);
        stockRepo.save(stock);
    }
}
