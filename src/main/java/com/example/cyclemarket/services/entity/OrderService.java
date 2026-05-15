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
import com.example.cyclemarket.repos.OrderRepo;
import com.example.cyclemarket.repos.ProductRepo;
import com.example.cyclemarket.services.SessionCartService;
import com.example.cyclemarket.services.StockService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
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

    @Transactional
    public void createOrder(String email, HttpSession session, CheckoutRequest checkoutRequest) {
        CartSnapshot cartSnapshot = sessionCartService.getCartSnapshot(session);

        if (cartSnapshot.getItems().isEmpty()) {
            throw new IllegalStateException("Cannot create order from empty cart");
        }

        List<Long> productIds = cartSnapshot.getItems().stream()
                .map(CartSnapshotItem::getProductId)
                .toList();

        Map<Long, Product> productsById = productRepo.findAllById(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        Order order = new Order();
        order.setDeliveryAddress(checkoutRequest.getDeliveryAddress());
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

    @Transactional
    public OrderDetailsView getOrder(Long id, String email) {
        Order order = orderRepo.findById(id).orElseThrow(OrderNotFoundException::new);
        if (!order.getUser().getEmail().equals(email)) {
            throw new OrderNotFoundException();
        }
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
                order.getDeliveryAddress(),
                order.getComment(),
                order.getStatus().getDisplayName(),
                order.getStatus().getCssClass()
        );
    }

    @Transactional
    public List<OrderView> getOrders(String email) {
        List<Order> ordersByUser = orderRepo.findOrdersByUser(userService.getByEmail(email));

        return ordersByUser.stream().map(
                order -> new OrderView(
                        order.getId(),
                        order.getCreatedAt(),
                        order.getTotalPrice(),
                        order.getItems().size(),
                        order.getRecipientFullName()
                )
        ).toList();
    }
}
