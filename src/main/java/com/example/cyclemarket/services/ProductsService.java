package com.example.cyclemarket.services;

import com.example.cyclemarket.entities.Product;
import com.example.cyclemarket.repos.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {
    private final ProductRepo productRepo;

    public ProductsService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
}
