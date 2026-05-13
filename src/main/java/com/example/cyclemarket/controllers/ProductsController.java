package com.example.cyclemarket.controllers;

import com.example.cyclemarket.services.CategoryService;
import com.example.cyclemarket.services.ProductsService;
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

    @GetMapping
    public String products(@RequestParam(required = false) Long categoryId,
                           @RequestParam(required = false) String sort,
                           Model model) {
        model.addAttribute("products", productsService.getProducts(categoryId, sort));
        model.addAttribute("rootCategories", categoryService.getAllParentCategories());
        model.addAttribute("selectedCategoryId", categoryId);
        return "product/products";
    }
}
