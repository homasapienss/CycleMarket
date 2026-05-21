package com.example.cyclemarket.repos;

import com.example.cyclemarket.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepo extends JpaRepository<Stock, Long> {
    List<Stock> findByShopId(Long shopId);

    boolean existsByShopIdAndProductId(Long id, Long productId);

    List<Stock> findAllByProductId(Long productId);

    Optional<Stock> findByShopIdAndProductId(Long shopId, Long productId);

    List<Stock> findStocksByShop_Id(Long shopId);
}
