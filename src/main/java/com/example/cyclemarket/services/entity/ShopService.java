package com.example.cyclemarket.services.entity;

import com.example.cyclemarket.dto.CreateShopReq;
import com.example.cyclemarket.entities.Shop;
import com.example.cyclemarket.exception.notfound.ShopNotFoundException;
import com.example.cyclemarket.repos.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepo shopRepo;

    public Shop getShopById(Long shopId) {
        return shopRepo.findById(shopId).orElseThrow(ShopNotFoundException::new);
    }

    public void createShop(CreateShopReq createShopReq) {
        Shop shopToCreate = new Shop();
        shopToCreate.setAddress(createShopReq.getAddress());
        shopToCreate.setShopName(createShopReq.getName());
        shopRepo.save(shopToCreate);
    }

    public List<Shop> getAllShops() {
        return shopRepo.findAll();
    }
}
