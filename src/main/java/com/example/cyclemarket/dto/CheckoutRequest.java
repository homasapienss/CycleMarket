package com.example.cyclemarket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CheckoutRequest {
    private String recipientFullName;
    private String recipientPhone;
    private String deliveryAddress;
    private String comment;
}
