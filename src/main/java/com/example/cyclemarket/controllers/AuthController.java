package com.example.cyclemarket.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/sign-in")
    public String showSignInPage() {
        return "sign-in";
    }

    @GetMapping("/sign-up")
    public String showSignUpPage() {
        return "sign-up";
    }
}
