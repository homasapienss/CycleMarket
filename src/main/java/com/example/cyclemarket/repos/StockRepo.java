package com.example.cyclemarket.repos;

import com.example.cyclemarket.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepo extends JpaRepository<Stock, Long> {
}
