package com.example.cyclemarket.controllers;

import com.example.cyclemarket.entities.Shop;
import com.example.cyclemarket.services.ShopContextService;
import com.example.cyclemarket.services.StockService;
import com.example.cyclemarket.services.entity.CategoryService;
import com.example.cyclemarket.services.product.ProductsService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductsController {
    private final ProductsService productsService;
    private final CategoryService categoryService;
    private final ShopContextService shopContextService;
    private final StockService stockService;

    @GetMapping
    public String products(@RequestParam(required = false) Long categoryId,
                           @RequestParam(required = false) String sort,
                           Model model,
                           HttpSession session) {
        Shop currentShop = shopContextService.getSelectedShop(session);
        Long shopId = null;
        if (currentShop != null ){
            shopId = currentShop.getId();
            model.addAttribute("productStockByShop", stockService.getProductStockByShop(shopId));
        } else {
            model.addAttribute("productShopCount", stockService.getProductShopCount());
            model.addAttribute("productAvailabilityByShop", stockService.getProductAvailabilityByShop());
            model.addAttribute("productSingleShopId", stockService.getProductSingleShopId());
        }
        model.addAttribute("products", productsService.getProducts(categoryId, sort, shopId));
        model.addAttribute("rootCategories", categoryService.getAllParentCategories());
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("shops", shopContextService.getAllShops());
        model.addAttribute("currentShop", currentShop);
        return "product/products";
    }
}
