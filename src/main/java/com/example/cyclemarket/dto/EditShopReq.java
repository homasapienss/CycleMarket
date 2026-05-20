package com.example.cyclemarket.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EditShopReq {
    private Long shopId;
    private String name;
    private String address;
}
