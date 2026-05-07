package com.example.cyclemarket.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartSnapshot {
    private List<CartSnapshotItem> items;
    private Integer totalPrice;
}
