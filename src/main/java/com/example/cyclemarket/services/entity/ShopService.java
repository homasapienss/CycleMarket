package com.example.cyclemarket.services.entity;

import com.example.cyclemarket.dto.CreateShopReq;
import com.example.cyclemarket.dto.EditShopReq;
import com.example.cyclemarket.dto.ShopView;
import com.example.cyclemarket.entities.Shop;
import com.example.cyclemarket.exception.notfound.ShopNotFoundException;
import com.example.cyclemarket.repos.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepo shopRepo;

    public Shop getById(Long shopId) {
        return shopRepo.findById(shopId).orElseThrow(ShopNotFoundException::new);
    }

    public void createShop(CreateShopReq createShopReq) {
        Shop shopToCreate = new Shop();
        shopToCreate.setAddress(createShopReq.getAddress());
        shopToCreate.setShopName(createShopReq.getName());
        shopRepo.save(shopToCreate);
    }

    public List<ShopView> getShopsView(String filter) {
        if (filter == null || filter.equals("") || filter.equals("all")) {
            return shopRepo.findAll()
                    .stream()
                    .map(this::mapShopToView)
                    .toList();
        }
        if (filter.equals("active")) {
            return shopRepo.findAllByActiveIsTrue()
                    .stream()
                    .map(this::mapShopToView).toList();
        }
        if (filter.equals("inactive")) {
            return shopRepo.findAllByActiveIsFalse()
                    .stream()
                    .map(this::mapShopToView).toList();
        }
        return shopRepo.findAll()
                .stream()
                .map(this::mapShopToView)
                .toList();
    }

    private ShopView mapShopToView(Shop shop) {
        return ShopView.builder()
                .id(shop.getId())
                .name(shop.getShopName())
                .address(shop.getAddress())
                .active(shop.isActive())
                .build();
    }

    public List<Shop> getAllShops() {
        return shopRepo.findAll();
    }

    public Shop getByName(String selectedShopName) {
        return shopRepo.findByShopName(selectedShopName)
                .orElseThrow(ShopNotFoundException::new);
    }

    public EditShopReq getEditShopReq(Long shopId) {
        Shop shop = shopRepo.findById(shopId).orElseThrow(ShopNotFoundException::new);
        return EditShopReq.builder()
                .address(shop.getAddress())
                .name(shop.getShopName())
                .shopId(shop.getId())
                .build();
    }

    @Transactional
    public void editShop(EditShopReq editShopReq) {
        Shop shop = shopRepo.findById(editShopReq.getShopId()).orElseThrow(ShopNotFoundException::new);
        shop.setAddress(editShopReq.getAddress());
        shop.setShopName(editShopReq.getName());
        shopRepo.save(shop);
    }

    public Shop getShopByName(String shopName) {
        return shopRepo.findByShopName(shopName).orElseThrow(ShopNotFoundException::new);
    }
}
