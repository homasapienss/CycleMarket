package com.example.cyclemarket.services.entity;

import com.example.cyclemarket.dto.CheckoutRequest;
import com.example.cyclemarket.dto.cart.CartSnapshot;
import com.example.cyclemarket.dto.cart.CartSnapshotItem;
import com.example.cyclemarket.dto.order.OrderDetailsView;
import com.example.cyclemarket.dto.order.OrderItemView;
import com.example.cyclemarket.dto.order.OrderView;
import com.example.cyclemarket.entities.*;
import com.example.cyclemarket.exception.notfound.OrderNotFoundException;
import com.example.cyclemarket.exception.notfound.ProductNotFoundException;
import com.example.cyclemarket.exception.notfound.ShopNotFoundException;
import com.example.cyclemarket.repos.OrderRepo;
import com.example.cyclemarket.repos.ProductRepo;
import com.example.cyclemarket.services.SessionCartService;
import com.example.cyclemarket.services.StockService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private final SessionCartService sessionCartService;
    private final UserService userService;
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final StockService stockService;
    private final ShopService shopService;

    @Transactional
    public void createOrder(String email, HttpSession session, CheckoutRequest checkoutRequest, Long shopId) {
        CartSnapshot cartSnapshot = sessionCartService.getCartSnapshot(session);

        if (shopId == null) {
            throw new ShopNotFoundException();
        }
        if (cartSnapshot.getItems().isEmpty()) {
            throw new IllegalStateException("Cannot create order from empty cart");
        }

        cartSnapshot.getItems()
                .forEach(item -> stockService.decreaseStock(item.getProductId(), shopId, item.getQuantity()));

        List<Long> productIds = cartSnapshot.getItems().stream()
                .map(CartSnapshotItem::getProductId)
                .toList();

        Map<Long, Product> productsById = productRepo.findAllById(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        Order order = new Order();
        order.setShop(shopService.getById(shopId));
        order.setRecipientFullName(checkoutRequest.getRecipientFullName());
        order.setRecipientPhone(checkoutRequest.getRecipientPhone());
        order.setComment(checkoutRequest.getComment());
        order.setStatus(OrderStatus.NEW);
        order.setUser(userService.getByEmail(email));
        order.setTotalPrice(cartSnapshot.getTotalPrice());
        List<OrderItem> orderItems = cartSnapshot.getItems()
                .stream()
                .map(cartItem -> {
                    Product product = productsById.get(cartItem.getProductId());
                    if (product == null) {
                        throw new ProductNotFoundException();
                    }
                    return OrderItem.builder()
                            .order(order)
                            .product(product)
                            .quantity(cartItem.getQuantity())
                            .priceSnapshot(cartItem.getUnitPrice())
                            .build();
                })
                .toList();
        order.setItems(orderItems);
        orderRepo.save(order);
        sessionCartService.clearCart(session);
    }

    @Transactional(readOnly = true)
    public OrderDetailsView getOrderDetailsByUser(Long id, String email) {
        Order order = orderRepo.findByIdAndUser_Email(id, email).orElseThrow(OrderNotFoundException::new);
        return getOrderDetailsByOrder(order);
    }

    @Transactional(readOnly = true)
    public List<OrderView> getOrderViewsByEmail(String email) {
        List<Order> ordersByUser = orderRepo.findOrdersByUser(userService.getByEmail(email));

        return ordersByUser.stream().map(
                order -> new OrderView(
                        order.getId(),
                        order.getCreatedAt(),
                        order.getTotalPrice(),
                        order.getItems().size(),
                        order.getRecipientFullName(),
                        order.getShop().getShopName()
                )
        ).toList();
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByShop(Long shopId) {
        return orderRepo.getOrdersByShop_Id(shopId);
    }

    public List<Order> getOrdersByManagersShop(Long shopId) {
        return orderRepo.findOrdersByShop_Id(shopId);
    }

    public List<Order> getOrders() {
        return orderRepo.findAll();
    }

    public List<Order> getOrdersByShopIdAndStatus(Long shopId, String status) {
        return orderRepo.findOrdersByShop_IdAndStatus(shopId, OrderStatus.valueOf(status));
    }

    public List<Order> getOrdersByStatus(String status) {
        return orderRepo.findOrdersByStatus(OrderStatus.valueOf(status));
    }

    public OrderDetailsView getOrderDetailsForStaff(Long orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow(OrderNotFoundException::new);
        return getOrderDetailsByOrder(order);
    }

    public OrderDetailsView getOrderDetailsByOrder(Order order) {
        return new OrderDetailsView(
                order.getId(),
                order.getCreatedAt(),
                order.getTotalPrice(),
                order.getItems().stream()
                        .map(orderItem -> {
                            List<ProductImage> productImages = orderItem.getProduct().getProductImages();
                            String imageUrl = productImages != null && !productImages.isEmpty()
                                    ? productImages.get(0).getImageUrl()
                                    : null;
                            return OrderItemView.builder()
                                    .productId(orderItem.getProduct().getId())
                                    .productName(orderItem.getProduct().getProductName())
                                    .quantity(orderItem.getQuantity())
                                    .priceSnapshot(orderItem.getPriceSnapshot())
                                    .lineTotal(orderItem.getQuantity() * orderItem.getPriceSnapshot())
                                    .imageUrl(imageUrl)
                                    .build();
                        }).toList(),
                order.getRecipientFullName(),
                order.getRecipientPhone(),
                order.getComment(),
                order.getStatus().getDisplayName(),
                order.getStatus().getCssClass(),
                order.getShop().getShopName()
        );
    }

    public Order getOrder(Long orderId) {
        return orderRepo.findById(orderId).orElseThrow(OrderNotFoundException::new);
    }

    public Order getOrderByIdAndShopId(Long orderId, Long managerShopId) {
        return orderRepo.findOrderByIdAndShop_Id(orderId, managerShopId)
                .orElseThrow(()->new AccessDeniedException("Нет доступа к заказу другого магазина"));
    }
}
