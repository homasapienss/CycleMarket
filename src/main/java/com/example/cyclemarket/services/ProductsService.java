package com.example.cyclemarket.services;

import com.example.cyclemarket.entities.Product;
import com.example.cyclemarket.repos.ProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductsService {
    private final ProductRepo productRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
}
