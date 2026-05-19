package com.example.cyclemarket.controllers;

import com.example.cyclemarket.dto.CheckoutRequest;
import com.example.cyclemarket.services.ShopContextService;
import com.example.cyclemarket.services.entity.OrderService;
import com.example.cyclemarket.services.SessionCartService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final SessionCartService sessionCartService;
    private final ShopContextService shopContextService;

    @GetMapping
    public String order(Model model,
                        Authentication authentication,
                        HttpSession session) {
        model.addAttribute("ordersViews", orderService.getOrders(authentication.getName()));
        model.addAttribute("shops", shopContextService.getAllShops());
        model.addAttribute("currentShop", shopContextService.getSelectedShop(session));
        return "order/orders";
    }

    @GetMapping("/{id}")
    public String order(@PathVariable Long id,
                        Model model,
                        Authentication authentication,
                        HttpSession session) {
        model.addAttribute("orderView", orderService.getOrder(id, authentication.getName()));
        model.addAttribute("shops", shopContextService.getAllShops());
        model.addAttribute("currentShop", shopContextService.getSelectedShop(session));
        return "order/order";
    }

    @PostMapping
    public String createOrder(HttpSession session,
                              Authentication authentication,
                              @Valid @ModelAttribute("checkoutForm") CheckoutRequest checkoutRequest,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            var currentShop = shopContextService.getSelectedShop(session);

            model.addAttribute("cartInfo",
                    sessionCartService.getCartView(session, currentShop != null ? currentShop.getId() : null));
            return "order/checkout";
        }
        orderService.createOrder(authentication.getName(), session, checkoutRequest);
        return "redirect:/orders";
    }


}
