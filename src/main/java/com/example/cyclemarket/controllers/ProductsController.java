package com.example.cyclemarket.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductsController {
    @GetMapping
    public String products(Model model) {
        return "products";
    }
}
