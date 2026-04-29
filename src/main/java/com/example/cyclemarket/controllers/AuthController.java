package com.example.cyclemarket.controllers;

import com.example.cyclemarket.dto.auth.AuthReq;
import com.example.cyclemarket.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/sign-in")
    public String showSignInPage() {
        return "sign-in";
    }

    @GetMapping("/sign-up")
    public String showSignUpPage() {
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String registerUser(@ModelAttribute AuthReq authReq, Model model) {
        try {
            authService.register(authReq.getUsername(), authReq.getPassword());
        } catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            return "sign-up";
        }
        return "redirect:/";
    }
}
