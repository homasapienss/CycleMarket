package com.example.cyclemarket.services;

import com.example.cyclemarket.dto.ProductAvailabilityRow;
import com.example.cyclemarket.entities.Stock;
import com.example.cyclemarket.exception.NotEnoughStockException;
import com.example.cyclemarket.exception.StockNotFoundException;
import com.example.cyclemarket.repos.StockRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Map<Long, Integer> getProductStockByShop(Long shopId) {
        return stockRepo.findByShopId(shopId).stream()
                .filter(stock -> stock.getQuantity() != null)
                .collect(Collectors.toMap(
                        stock -> stock.getProduct().getId(),
                        Stock::getQuantity
                ));
    }

    public Map<Long, Integer> getProductShopCount() {
        return stockRepo.findAll().stream()
                .filter(stock -> stock.getQuantity() != null && stock.getQuantity() > 0)
                .collect(Collectors.groupingBy(
                        stock -> stock.getProduct().getId(),
                        Collectors.summingInt(stock -> 1)
                ));
    }
    public Map<Long, List<ProductAvailabilityRow>> getProductAvailabilityByShop() {
        return stockRepo.findAll().stream()
                .filter(stock -> stock.getQuantity() != null && stock.getQuantity() > 0)
                .sorted(Comparator.comparing(stock -> stock.getShop().getShopName()))
                .collect(Collectors.groupingBy(
                        stock -> stock.getProduct().getId(),
                        Collectors.mapping(
                                stock -> new ProductAvailabilityRow(
                                        stock.getShop().getId(),
                                        stock.getShop().getShopName(),
                                        stock.getQuantity()
                                ),
                                Collectors.toList()
                        )
                ));
    }

    public List<ProductAvailabilityRow> getProductAvailabilityByShop(Long productId) {
        return stockRepo.findAllByProductId(productId).stream()
                .filter(stock -> stock.getQuantity() != null && stock.getQuantity() > 0)
                .sorted(Comparator.comparing(stock -> stock.getShop().getShopName()))
                .map(stock -> new ProductAvailabilityRow(
                        stock.getShop().getId(),
                        stock.getShop().getShopName(),
                        stock.getQuantity()
                ))
                .toList();
    }

    public Integer getCurrentProductStock(Long productId, Long shopId) {
        return stockRepo.findByShopId(shopId).stream()
                .filter(stock -> stock.getProduct().getId().equals(productId))
                .map(Stock::getQuantity)
                .findFirst()
                .orElse(0);
    }

    public Integer getProductShopCount(Long productId) {
        return (int) stockRepo.findAllByProductId(productId).stream()
                .filter(stock -> stock.getQuantity() != null && stock.getQuantity() > 0)
                .count();
    }

    public Map<Long, Long> getProductSingleShopId() {
        return stockRepo.findAll().stream()
                .filter(stock -> stock.getQuantity() != null && stock.getQuantity() > 0)
                .collect(Collectors.groupingBy(stock -> stock.getProduct().getId()))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() == 1)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().get(0).getShop().getId()
                ));
    }

    public void decreaseStock(Long productId, Long shopId, Integer quantity) {
        Stock stock = stockRepo.findByShopIdAndProductId(shopId, productId)
                .orElseThrow(StockNotFoundException::new);

        if (stock.getQuantity() < quantity) {
            throw new NotEnoughStockException();
        }

        stock.setQuantity(stock.getQuantity() - quantity);
        stockRepo.save(stock);
    }
}
