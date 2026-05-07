package com.example.cyclemarket.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CartView {
    List<CartItemView> items;
    Integer totalAmount;
}
