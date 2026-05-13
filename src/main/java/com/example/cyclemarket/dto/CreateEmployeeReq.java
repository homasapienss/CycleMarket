package com.example.cyclemarket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeReq {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Long shopId;
//    private Long userId;
}
