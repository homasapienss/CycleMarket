package com.example.cyclemarket.dto;

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
}
