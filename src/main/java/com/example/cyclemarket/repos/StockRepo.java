package com.example.cyclemarket.repos;

import com.example.cyclemarket.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepo extends JpaRepository<Stock, Long> {
    List<Stock> findByShopId(Long shopId);

    boolean existsByShopIdAndProductId(Long id, Long productId);

    List<Stock> findAllByProductId(Long productId);
}
