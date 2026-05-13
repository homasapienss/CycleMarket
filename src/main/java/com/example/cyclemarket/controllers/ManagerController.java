package com.example.cyclemarket.controllers;

import com.example.cyclemarket.services.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
