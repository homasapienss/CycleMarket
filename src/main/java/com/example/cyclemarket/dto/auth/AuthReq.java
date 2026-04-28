package com.example.cyclemarket.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthReq {
    private final String username;
    private final String password;
}
