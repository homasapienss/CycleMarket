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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("ordersViews", orderService.getOrderViewsByEmail(authentication.getName()));
        model.addAttribute("shops", shopContextService.getAllShops());
        model.addAttribute("currentShop", shopContextService.getSelectedShop(session));
        return "order/orders";
    }

    @GetMapping("/{id}")
    public String order(@PathVariable Long id,
                        Model model,
                        Authentication authentication,
                        HttpSession session) {
        model.addAttribute("orderView", orderService.getOrderDetailsByUser(id, authentication.getName()));
        model.addAttribute("shops", shopContextService.getAllShops());
        model.addAttribute("currentShop", shopContextService.getSelectedShop(session));
        return "order/order";
    }

    @PostMapping
    public String createOrder(HttpSession session,
                              Authentication authentication,
                              @Valid @ModelAttribute("checkoutForm") CheckoutRequest checkoutRequest,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        var currentShop = shopContextService.getSelectedShop(session);
        Long shopId = currentShop != null ? currentShop.getId() : null;
        if (bindingResult.hasErrors()) {


            model.addAttribute("cartInfo",
                    sessionCartService.getCartView(session, shopId));
            model.addAttribute("shops", shopContextService.getAllShops());
            model.addAttribute("currentShop", shopContextService.getSelectedShop(session));
            return "order/checkout";
        }
        orderService.createOrder(authentication.getName(), session, checkoutRequest, shopId);
        redirectAttributes.addFlashAttribute("successMessage", "Заказ успешно создан.");
        return "redirect:/orders";
    }


}
