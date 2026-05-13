package com.example.cyclemarket.exception.notfound;


import com.example.cyclemarket.exception.ApplicationException;

public class ShopNotFoundException extends ApplicationException {
    public ShopNotFoundException() {
        super("Магазин не найден");
    }
}
