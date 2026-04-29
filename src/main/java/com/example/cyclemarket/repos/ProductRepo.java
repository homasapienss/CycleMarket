package com.example.cyclemarket.repos;

import com.example.cyclemarket.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findByProductName(String productName);
    Optional<Product> findById(Long aLong);
}
