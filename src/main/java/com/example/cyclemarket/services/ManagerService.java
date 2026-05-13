package com.example.cyclemarket.services;

import com.example.cyclemarket.dto.ProductStock;
import com.example.cyclemarket.entities.Product;
import com.example.cyclemarket.entities.Shop;
import com.example.cyclemarket.entities.Stock;
import com.example.cyclemarket.exception.notfound.ProductNotFoundException;
import com.example.cyclemarket.repos.ProductRepo;
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
    private final ProductRepo productRepo;

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

    @Transactional(readOnly = true)
    public List<Product> getNotStockByShop(String name) {
        Long shopId = employeeService.getShopIdByEmployeeName(name);
        return productRepo.findProductsNotInShopStock(shopId);
    }

    @Transactional
    public void addToStock(String name, Long productId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Кол-во не может быть отрицательным");
        }
        Shop shop = employeeService.getCurrentManagerShop(name);
        Product product = productRepo.findById(productId).orElseThrow(ProductNotFoundException::new);

        if (stockRepo.existsByShopIdAndProductId(shop.getId(), productId)) {
            throw new IllegalStateException("Продукт уже добавлен на склад");
        }
        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setQuantity(quantity);
        stock.setShop(shop);
        stockRepo.save(stock);
    }
}
