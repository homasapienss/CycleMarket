package com.example.cyclemarket.exception;

public class NotEnoughStockException extends ApplicationException {
    public NotEnoughStockException() {
        super("Измените кол-во товара, вы взяли больше чем есть в наличии");
    }
}
