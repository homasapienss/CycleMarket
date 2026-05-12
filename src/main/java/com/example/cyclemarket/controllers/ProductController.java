package com.example.cyclemarket.controllers;

import com.example.cyclemarket.dto.ProductCreateRequest;
import com.example.cyclemarket.repos.ManufacturerRepo;
import com.example.cyclemarket.services.CategoryService;
import com.example.cyclemarket.services.ManufacturerService;
import com.example.cyclemarket.services.ProductManagementService;
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

    @GetMapping("/{id}")
    public String product(@PathVariable String id, Model model) {
        model.addAttribute("product", productManagementService.getProductById(Long.valueOf(id)));
        return "product";
    }
    @GetMapping("/new")
    @PreAuthorize("hasRole('MANAGER')")
    public String createForm(Model model) {
        model.addAttribute("productForm", new ProductCreateRequest());
        model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());
        model.addAttribute("categories", categoryService.getWeakCategories());
        return "product-create";
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('MANAGER')")
    public String createProduct(@ModelAttribute("productForm") ProductCreateRequest productForm) throws Exception {
        productManagementService.createProduct(productForm);
        return "redirect:/products";
    }
}
