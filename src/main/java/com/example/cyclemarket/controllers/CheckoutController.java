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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {
    private final SessionCartService sessionCartService;
    private final ShopContextService shopContextService;

    @GetMapping
    public String getCheckout(Model model,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        var currentShop = shopContextService.getSelectedShop(session);
        Long shopId = currentShop != null ? currentShop.getId() : null;

        if (!sessionCartService.isCartAvailableForCheckout(session, shopId)) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Количество одного или нескольких товаров превышает остаток на складе. Проверьте корзину."
            );
            return "redirect:/cart";
        }

        model.addAttribute("cartInfo", sessionCartService.getCartView(session, shopId));
        model.addAttribute("shops", shopContextService.getAllShops());
        model.addAttribute("currentShop", shopContextService.getSelectedShop(session));
        model.addAttribute("checkoutForm", new CheckoutRequest());
        return "order/checkout";
    }
}
