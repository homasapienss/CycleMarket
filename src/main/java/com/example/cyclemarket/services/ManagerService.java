package com.example.cyclemarket.services;

import com.example.cyclemarket.dto.ProductStock;
import com.example.cyclemarket.entities.Product;
import com.example.cyclemarket.entities.Stock;
import com.example.cyclemarket.repos.StockRepo;
import com.example.cyclemarket.services.entity.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ManagerService {
    private final EmployeeService employeeService;
    private final StockRepo stockRepo;

    @Transactional
    public List<ProductStock> getStockByShop(String name) {
        Long shopId = employeeService.getShopIdByEmployeeName(name);
        List<Stock> byShopId = stockRepo.findByShopId(shopId);

        return byShopId.stream().map(stock -> {
            Product product = stock.getProduct();
            return ProductStock.builder()
                    .id(product.getId())
                    .productName(product.getProductName())
                    .productPrice(product.getProductPrice())
                    .quantity(stock.getQuantity())
                    .build();
        }).toList();
    }
}
