package com.example.cyclemarket.controllers;

import com.example.cyclemarket.services.SessionCartService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {
    private final SessionCartService sessionCartService;

    @GetMapping
    public String getCartItems(HttpSession session, Model model) {
        model.addAttribute("cartInfo", sessionCartService.getCartInfo(session));
        return "cart";
    }

    @PostMapping("/item/{id}")
    public String addItem(@PathVariable Long id,
                          @RequestParam(defaultValue = "1") Integer quantity,
                          HttpSession session) {
        sessionCartService.addItem(id, quantity, session);
        return "redirect:/cart";
    }

    @PostMapping("/item/{productId}/quantity")
    public String changeQuantity(@PathVariable Long productId,
                                 @RequestParam Integer delta,
                                 HttpSession session) {
        sessionCartService.changeItemQuantity(productId, delta, session);
        return "redirect:/cart";
    }

    @PostMapping("/item/{productId}/remove")
    public String removeItem(@PathVariable Long productId, HttpSession session) {
        sessionCartService.removeItem(productId, session);
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(HttpSession session) {
        sessionCartService.clearCart(session);
        return "redirect:/cart";
    }
}
