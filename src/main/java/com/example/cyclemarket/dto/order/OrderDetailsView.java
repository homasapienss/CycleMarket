package com.example.cyclemarket.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDetailsView {
    private Long orderId;
    private LocalDateTime createdAt;
    private Integer totalPrice;
    private List<OrderItemView> items;
    private String recipientFullName;
    private String recipientPhone;
    private String deliveryAddress;
    private String comment;
    private String statusDisplayName;
    private String statusCssClass;
}
