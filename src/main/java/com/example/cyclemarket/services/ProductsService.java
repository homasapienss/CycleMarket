package com.example.cyclemarket.services;

import com.example.cyclemarket.entities.Category;
import com.example.cyclemarket.entities.Product;
import com.example.cyclemarket.repos.CategoryRepo;
import com.example.cyclemarket.repos.ProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductsService {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepo.findAllByCategories_Id(categoryId);
    }

    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }
}
