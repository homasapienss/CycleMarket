package com.example.cyclemarket.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EditEmployeeReq {
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String shopName;
}
