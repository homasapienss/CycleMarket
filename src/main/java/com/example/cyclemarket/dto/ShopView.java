package com.example.cyclemarket.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ShopView {
    private Long id;
    private String name;
    private String address;
    private boolean active;
}
