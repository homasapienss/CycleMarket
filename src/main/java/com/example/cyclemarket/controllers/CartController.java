package com.example.cyclemarket.controllers;

import com.example.cyclemarket.services.SessionCartService;
import com.example.cyclemarket.services.ShopContextService;
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
    private final ShopContextService shopContextService;

    @GetMapping
    public String getCartItems(HttpSession session, Model model) {
        var currentShop = shopContextService.getSelectedShop(session);

        model.addAttribute("cartInfo",
                sessionCartService.getCartView(session, currentShop != null ? currentShop.getId() : null));
        model.addAttribute("shops", shopContextService.getAllShops());
        model.addAttribute("currentShop", currentShop);

        return "cart";
    }

    @PostMapping("/item/{id}")
    public String addItem(@PathVariable Long id,
                          @RequestParam(defaultValue = "1") Integer quantity,
                          @RequestParam(required = false) Long shopId,
                          HttpSession session) {
        if (shopContextService.getSelectedShop(session) == null && shopId != null) {
            shopContextService.setSelectedShop(shopId, session);
        }
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
