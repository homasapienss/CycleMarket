package com.example.cyclemarket.controllers;

import com.example.cyclemarket.services.ManagerService;
import com.example.cyclemarket.services.entity.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;
    private final ShopService shopService;

    @GetMapping
    public String getManagerPage() {
        return "manager/manager";
    }

    @GetMapping("/stock")
    public String getStockShop(Model model,
                               Authentication authentication,
                               @RequestParam(required = false) String mode,
                               @RequestParam(required = false) Long shopId) {
        model.addAttribute("shopStock", managerService.getStockByShop(authentication, shopId));
        model.addAttribute("shops", shopService.getAllShops());
        return "manager/stock";
    }

    @GetMapping("/orders")
    public String getOrdersShop(Model model,
                                Authentication authentication,
                                @RequestParam(required = false) String status,
                                @RequestParam(required = false) Long shopId) {
        model.addAttribute("shopOrders", managerService.getOrdersByShop(authentication, shopId, status));
        model.addAttribute("shops", shopService.getAllShops());
        return "manager/orders";
    }

    @GetMapping("/orders/{id}")
    public String getOrder(Model model,
                           @PathVariable("id") Long orderId) {
        model.addAttribute("orderView", managerService.getOrderViewForStaff(orderId));
        model.addAttribute("staffView", true);
        return "order/order";
    }

    @GetMapping("/stock/new")
    public String getNotStockShop(Model model,
                                  Authentication authentication) {
        model.addAttribute("notShopStock", managerService.getNotStockByShop(authentication.getName()));
        return "manager/stock";
    }

    @PostMapping("/stock/new")
    public String addToStock(Authentication authentication,
                             @RequestParam("productId") Long productId,
                             @RequestParam("quantity") Integer quantity) {
        managerService.addToStock(authentication.getName(), productId, quantity);
        return "redirect:/manager";
    }
}
