package com.example.cyclemarket.controllers;

import com.example.cyclemarket.services.ProductsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductsController {
    private final ProductsService productsService;

    @GetMapping
    public String products(Model model) {
        model.addAttribute("products", productsService.getAllProducts());
        return "products";
    }
}
