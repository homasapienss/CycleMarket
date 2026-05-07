package com.example.cyclemarket.exception.notfound;

import com.example.cyclemarket.exception.ApplicationException;

public class OrderNotFoundException extends ApplicationException {
    public OrderNotFoundException() {
        super("Заказ не найден");
    }
}
