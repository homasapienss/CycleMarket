package com.example.cyclemarket.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItemView {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Integer priceSnapshot;
    private Integer lineTotal;
    private String imageUrl;
}
