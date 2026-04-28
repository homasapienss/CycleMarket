package com.example.cyclemarket.controllers;

import com.example.cyclemarket.dto.ProductCreateRequest;
import com.example.cyclemarket.repos.ManufacturerRepo;
import com.example.cyclemarket.services.ProductManagementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ManufacturerRepo manufacturerRepo;
    private final ProductManagementService productManagementService;

    @GetMapping("/{id}")
    public String product(@PathVariable String id, Model model) {
        return "product";
    }
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("productForm", new ProductCreateRequest());
        model.addAttribute("manufacturers", manufacturerRepo.findAll());
        return "product-create";
    }

    @PostMapping("/new")
    public String createProduct(@ModelAttribute("productForm") ProductCreateRequest productForm) throws Exception {
        productManagementService.createProduct(productForm);
        return "redirect:/products";
    }
}
