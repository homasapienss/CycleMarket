package com.example.cyclemarket.controllers;

import com.example.cyclemarket.entities.OrderStatus;
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
        boolean admin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if ("missing".equals(mode)) {
            if (admin && shopId == null) {
                model.addAttribute("shopSelectionRequired", true);
                model.addAttribute("shops", shopService.getAllShops());
                return "manager/stock";
            }
            model.addAttribute("notShopStock", managerService.getMissingProductsByShop(authentication, shopId));
        } else {
            model.addAttribute("shopStock", managerService.getStockByShop(authentication, shopId));
        }
        model.addAttribute("shops", shopService.getAllShops());
        return "manager/stock";
    }

    @PostMapping("/stock/new")
    public String addToStock(@RequestParam Long productId,
                             @RequestParam(required = false) Long shopId,
                             @RequestParam Integer quantity, Authentication auth) {
        managerService.addToStock(productId, shopId, quantity, auth);
        return "redirect:/manager/stock";
    }

    @GetMapping("/orders")
    public String getOrdersShop(Model model,
                                Authentication authentication,
                                @RequestParam(required = false) String status,
                                @RequestParam(required = false) Long shopId) {
        model.addAttribute("shopOrders", managerService.getOrdersForScope(authentication, shopId, status));
        model.addAttribute("shops", shopService.getAllShops());
        return "manager/orders";
    }

    @GetMapping("/orders/{id}")
    public String getOrder(Model model,
                           @PathVariable("id") Long orderId,
                           Authentication authentication) {
        model.addAttribute("orderView", managerService.getOrderViewForStaff(orderId, authentication));
        model.addAttribute("staffView", true);
        return "order/order";
    }

    @PostMapping("/orders/{id}/status")
    public String setOrderStatus(@PathVariable("id") Long orderId,
                                 @RequestParam(name = "status") OrderStatus status,
                                 Authentication authentication) {
        managerService.setOrderStatus(orderId, status, authentication);
        return "redirect:/manager/orders/" + orderId;
    }
}
