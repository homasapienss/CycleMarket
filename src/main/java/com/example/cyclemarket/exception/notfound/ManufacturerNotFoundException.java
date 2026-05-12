package com.example.cyclemarket.exception.notfound;

import com.example.cyclemarket.exception.ApplicationException;

public class ManufacturerNotFoundException extends ApplicationException {
    public ManufacturerNotFoundException() {
        super("Производитель не найден");
    }
}
