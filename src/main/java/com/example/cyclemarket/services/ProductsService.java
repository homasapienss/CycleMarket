package com.example.cyclemarket.services;

import com.example.cyclemarket.entities.Category;
import com.example.cyclemarket.entities.Product;
import com.example.cyclemarket.repos.ProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductsService {
    private final ProductRepo productRepo;
    private final CategoryService categoryService;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        Category categoryById = categoryService.getCategoryById(categoryId);
        if (categoryById.getChildren() == null || categoryById.getChildren().isEmpty()) {
            return productRepo.findAllByCategories_Id(categoryId);
        }
        List<Long> childIds = categoryById.getChildren().stream()
                .map(Category::getId)
                .toList();
        return productRepo.findAllByCategories_IdIn(childIds);
    }
}
