package com.example.cyclemarket.services.entity;

import com.example.cyclemarket.entities.Product;
import com.example.cyclemarket.exception.notfound.ProductNotFoundException;
import com.example.cyclemarket.repos.ProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepo productRepo;

    public Product getById(Long id) {
        return productRepo.findById(id).orElseThrow(ProductNotFoundException::new);
    }
}
