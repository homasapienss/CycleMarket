package com.example.cyclemarket.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartItemView {
    private Long productId;
    private String productName;
    private Integer price;
    private Integer quantity;
    private Integer lineTotal;
    private String imageUrl;

}
