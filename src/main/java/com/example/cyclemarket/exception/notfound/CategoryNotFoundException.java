package com.example.cyclemarket.exception.notfound;

import com.example.cyclemarket.exception.ApplicationException;

public class CategoryNotFoundException extends ApplicationException {
    public CategoryNotFoundException() {
        super("Категория не найдена");
    }
}
