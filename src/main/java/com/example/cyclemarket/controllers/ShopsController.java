package com.example.cyclemarket.controllers;

import com.example.cyclemarket.services.ShopContextService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopsController {
    private final ShopContextService shopContextService;

    @PostMapping("/select")
    public String selectShop(HttpSession session,
                             @RequestParam(required = false) Long shopId) {
        if (shopId == null) {
            shopContextService.clearSelectedShop(session);
        } else {
            shopContextService.setSelectedShop(shopId, session);
        }
        return "redirect:/products";
    }
}
