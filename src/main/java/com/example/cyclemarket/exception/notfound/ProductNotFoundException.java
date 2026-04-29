package com.example.cyclemarket.exception.notfound;

import com.example.cyclemarket.exception.ApplicationException;

public class ProductNotFoundException extends ApplicationException {
    public ProductNotFoundException() {
        super("Product not found");
    }
}
