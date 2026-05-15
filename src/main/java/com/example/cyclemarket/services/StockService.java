package com.example.cyclemarket.services;

import com.example.cyclemarket.entities.Stock;
import com.example.cyclemarket.repos.StockRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class StockService {
    private final StockRepo stockRepo;

    public boolean isProductsAvailable(Map<Long, Integer> productsById) {
        for (Map.Entry<Long, Integer> entry : productsById.entrySet()) {
            int stockAllShops = 0;
            List<Stock> allByProductId = stockRepo.findAllByProductId(entry.getKey());
            Integer value = entry.getValue();
            for (Stock stock : allByProductId) {
                stockAllShops+=stock.getQuantity();
            }
            if (stockAllShops < value) {
                return false;
            }
        }
        return true;
    }
}
