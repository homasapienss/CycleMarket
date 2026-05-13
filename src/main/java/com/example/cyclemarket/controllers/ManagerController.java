package com.example.cyclemarket.controllers;

import com.example.cyclemarket.services.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @GetMapping
    public String getManagerPage() {
        return "manager/manager";
    }

    @GetMapping("/stock")
    public String getStockShop(Model model,
                               Authentication authentication) {
        model.addAttribute("shopStock", managerService.getStockByShop(authentication.getName()));
        return "manager/stock";
    }

    @GetMapping("/stock/new")
    public String getNotStockShop(Model model,
                                  Authentication authentication) {
        model.addAttribute("notShopStock", managerService.getNotStockByShop(authentication.getName()));
        return "manager/new-stock";
    }

    @PostMapping("/stock/new")
    public String addToStock(Model model,
                             Authentication authentication,
                             @RequestParam("productId") Long productId,
                             @RequestParam("quantity") Integer quantity) {
        managerService.addToStock(authentication.getName(), productId, quantity);
        return "redirect:/manager";
    }
}
