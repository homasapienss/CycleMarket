package com.example.cyclemarket.exception;

public class StockNotFoundException extends ApplicationException {
    public StockNotFoundException() {
        super("Остатки не найдены");
    }
}
