package com.example.cyclemarket.repos;

import com.example.cyclemarket.entities.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepo extends JpaRepository<Shop, Long> {
    Optional<Shop> findByShopName(String shopName);
}
