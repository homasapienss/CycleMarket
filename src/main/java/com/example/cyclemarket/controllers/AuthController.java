package com.example.cyclemarket.controllers;

import com.example.cyclemarket.dto.auth.AuthReq;
import com.example.cyclemarket.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/sign-in")
    public String showSignInPage(Authentication authentication) {
        return isAuthenticated(authentication) ? "redirect:/products" : "auth/sign-in";
    }

    @GetMapping("/sign-up")
    public String showSignUpPage(Authentication authentication) {
        return isAuthenticated(authentication) ? "redirect:/products" : "auth/sign-up";
    }

    @PostMapping("/sign-up")
    public String registerUser(@ModelAttribute AuthReq authReq, Model model) {
        try {
            authService.register(authReq.getUsername(), authReq.getPassword());
        } catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/sign-up";
        }
        return "redirect:/sign-in";
    }
    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
    }
}
