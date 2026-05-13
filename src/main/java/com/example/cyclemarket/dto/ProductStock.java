package com.example.cyclemarket.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductStock {
    private Long id;
    private String productName;
    private Integer productPrice;
    private int quantity;
}
