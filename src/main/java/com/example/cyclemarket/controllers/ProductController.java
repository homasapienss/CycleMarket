package com.example.cyclemarket.controllers;

import com.example.cyclemarket.dto.ProductCreateRequest;
import com.example.cyclemarket.services.ShopContextService;
import com.example.cyclemarket.services.StockService;
import com.example.cyclemarket.services.entity.CategoryService;
import com.example.cyclemarket.services.entity.ManufacturerService;
import com.example.cyclemarket.services.product.ProductManagementService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ManufacturerService manufacturerService;
    private final CategoryService categoryService;
    private final ProductManagementService productManagementService;
    private final ShopContextService shopContextService;
    private final StockService stockService;

    @GetMapping("/{id}")
    public String product(@PathVariable Long id,
                          Model model,
                          HttpSession session) {
        var product = productManagementService.getProductById(id);
        var currentShop = shopContextService.getSelectedShop(session);

        model.addAttribute("product", product);
        model.addAttribute("shops", shopContextService.getAllShops());
        model.addAttribute("currentShop", currentShop);
        model.addAttribute("productAvailabilityByShop", stockService.getProductAvailabilityByShop(id));
        if (currentShop != null) {
            model.addAttribute("currentProductStock",
                    stockService.getCurrentProductStock(id, currentShop.getId()));
        } else {
            model.addAttribute("productShopCount",
                    stockService.getProductShopCount(id));
        }
        return "product/product";
    }
    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String createForm(Model model) {
        model.addAttribute("productForm", new ProductCreateRequest());
        model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());
        model.addAttribute("categories", categoryService.getWeakCategories());
        return "product/product-create";
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String createProduct(@ModelAttribute("productForm") ProductCreateRequest productForm) throws Exception {
        productManagementService.createProduct(productForm);
        return "redirect:/products";
    }
}
