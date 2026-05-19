package com.example.cyclemarket.exception;

public class NotEnoughStockException extends ApplicationException {
    public NotEnoughStockException() {
        super("Нельзя добавить больше товара, чем есть в наличии");
    }
}
