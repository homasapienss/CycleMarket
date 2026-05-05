package com.example.cyclemarket.services;

import com.example.cyclemarket.dto.CartView;
import com.example.cyclemarket.dto.CartItemView;
import com.example.cyclemarket.entities.Product;
import com.example.cyclemarket.repos.ProductRepo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class SessionCartService {
    private static final String CART_ATTRIBUTE = "cart";
    private final ProductRepo productRepo;

    public void addItem(Long id, Integer quantity, HttpSession session) {
        Map<Long, Integer> cart = getOrCreateCart(session);
        cart.merge(id, quantity, Integer::sum);
    }

    public void removeItem(Long id, HttpSession session) {
        Map<Long, Integer> cart = getOrCreateCart(session);
        cart.remove(id);
    }

    public void clearCart(HttpSession session) {
        session.removeAttribute(CART_ATTRIBUTE);
    }

    public CartView getCartInfo(HttpSession session) {
        Map<Long, Integer> cart = getOrCreateCart(session);

        if (cart.isEmpty()) {
            return new CartView(List.of(), 0);
        }

        List<Product> products = productRepo.findAllById(cart.keySet());
        List<CartItemView> cartItems = products.stream()
                .map(product -> {
                    Integer quantity = cart.getOrDefault(product.getId(), 0);
                    String imageUrl = product.getProductImages() != null && !product.getProductImages().isEmpty()
                            ? product.getProductImages().get(0).getImageUrl()
                            : null;
                    return new CartItemView(
                            product.getId(),
                            product.getProductName(),
                            product.getProductPrice(),
                            quantity,
                            product.getProductPrice() * quantity,
                            imageUrl
                    );
                }).toList();
        Integer total = cartItems.stream()
                .map(CartItemView::getLineTotal)
                .reduce(0, Integer::sum);
        return new CartView(cartItems, total);
    }

    @SuppressWarnings("unchecked")
    private Map<Long, Integer> getOrCreateCart(HttpSession session) {
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute(CART_ATTRIBUTE);
        if (cart == null) {
            cart = new ConcurrentHashMap<>();
            session.setAttribute(CART_ATTRIBUTE, cart);
        }
        return cart;
    }
}
