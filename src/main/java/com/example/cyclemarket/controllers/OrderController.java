package com.example.cyclemarket.controllers;

import com.example.cyclemarket.dto.CheckoutRequest;
import com.example.cyclemarket.services.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public String order(Model model,
                        Authentication authentication) {
        model.addAttribute("ordersViews", orderService.getOrders(authentication.getName()));
        return "orders";
    }

    @GetMapping("/{id}")
    public String order(@PathVariable Long id, Model model,
                        Authentication authentication) {
        model.addAttribute("orderView", orderService.getOrder(id, authentication.getName()));
        return "order";
    }

    @PostMapping
    public String createOrder(HttpSession session,
                              Authentication authentication,
                              @ModelAttribute("checkoutForm") CheckoutRequest checkoutRequest) {
        orderService.createOrder(authentication.getName(), session, checkoutRequest);
        return "redirect:/orders";
    }


}
