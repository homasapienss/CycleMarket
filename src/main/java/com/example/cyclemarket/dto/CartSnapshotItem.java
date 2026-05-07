package com.example.cyclemarket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartSnapshotItem {
    private Long productId;
    private Integer quantity;
    private Integer unitPrice;
}
