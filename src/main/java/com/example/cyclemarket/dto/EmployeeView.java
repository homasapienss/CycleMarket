package com.example.cyclemarket.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeView {
    private Integer id;
    private String firstName;
    private String lastName;
    private String shopName;
    private String roleName;
    private boolean active;
}
