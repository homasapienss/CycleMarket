package com.example.cyclemarket.services;

import com.example.cyclemarket.entities.Shop;
import com.example.cyclemarket.services.entity.ShopService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ShopContextService {
    private final ShopService shopService;
    private final SessionCartService sessionCartService;

    public Long getSelectedShopId(HttpSession session) {
        return (Long) session.getAttribute("selectedShopId");
    }

    public Shop getSelectedShop(HttpSession session) {
        Long shopId = getSelectedShopId(session);
        if (shopId == null) {
            return null;
        }
        return shopService.getById(shopId);
    }

    public void setSelectedShop(Long shopId, HttpSession session) {
        session.setAttribute("selectedShopId",  shopId);
        sessionCartService.clearCart(session);
    }

    public void clearSelectedShop(HttpSession session) {
        session.removeAttribute("selectedShopId");
        sessionCartService.clearCart(session);
    }

    public List<Shop> getAllShops() {
        return shopService.getAllShops();
    }
}
