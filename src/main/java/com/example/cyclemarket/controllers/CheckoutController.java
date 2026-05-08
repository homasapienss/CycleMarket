package com.example.cyclemarket.controllers;

import com.example.cyclemarket.dto.CheckoutRequest;
import com.example.cyclemarket.services.SessionCartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {
    private final SessionCartService sessionCartService;

    @GetMapping
    public String getCheckout(Model model,
                              HttpSession session) {
        model.addAttribute("cartInfo", sessionCartService.getCartView(session));
        model.addAttribute("checkoutForm", new CheckoutRequest());
        return "checkout";
    }
}
