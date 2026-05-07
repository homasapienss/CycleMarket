package com.example.cyclemarket.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderView {
    private Long orderId;
    private LocalDateTime createdAt;
    private Integer totalPrice;
    private Integer itemsCount;
}
