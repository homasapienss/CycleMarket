package com.example.cyclemarket.dto.order;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderItemView {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Integer priceSnapshot;
    private Integer lineTotal;
    private String imageUrl;
}
