package com.example.cyclemarket.controllers;

import com.example.cyclemarket.dto.CheckoutRequest;
import com.example.cyclemarket.services.SessionCartService;
import com.example.cyclemarket.services.ShopContextService;
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
    private final ShopContextService shopContextService;

    @GetMapping
    public String getCheckout(Model model,
                              HttpSession session) {
        var currentShop = shopContextService.getSelectedShop(session);

        model.addAttribute("cartInfo",
                sessionCartService.getCartView(session, currentShop != null ? currentShop.getId() : null));
        model.addAttribute("checkoutForm", new CheckoutRequest());
        model.addAttribute("shops", shopContextService.getAllShops());
        model.addAttribute("currentShop", shopContextService.getSelectedShop(session));
        return "order/checkout";
    }
}
