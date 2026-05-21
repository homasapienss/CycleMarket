package com.example.cyclemarket.controllers;

import com.example.cyclemarket.services.ManagerService;
import com.example.cyclemarket.services.entity.EmployeeService;
import com.example.cyclemarket.services.entity.ShopService;
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
    private final ShopService shopService;
    private final EmployeeService employeeService;

    @GetMapping
    public String getManagerPage(Model model, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("managerUsername", authentication.getName());

        if (isAdmin) {
            model.addAttribute("shops", shopService.getAllShops());
        } else {
            model.addAttribute("managerShopName",
                    employeeService.getCurrentManagerShop(authentication.getName()).getShopName());
        }

        return "manager/manager";
    }

    @GetMapping("/stock")
    public String getStockShop(Model model,
                               Authentication authentication,
                               @RequestParam(required = false) Long shopId) {
        model.addAttribute("shopStock", managerService.getStockByShop(authentication, shopId));
        return "manager/stock";
    }

    @GetMapping("/stock/new")
    public String getNotStockShop(Model model,
                                  Authentication authentication) {
        model.addAttribute("notShopStock", managerService.getNotStockByShop(authentication.getName()));
        return "manager/new-stock";
    }

    @PostMapping("/stock/new")
    public String addToStock(Authentication authentication,
                             @RequestParam("productId") Long productId,
                             @RequestParam("quantity") Integer quantity) {
        managerService.addToStock(authentication.getName(), productId, quantity);
        return "redirect:/manager";
    }
}
