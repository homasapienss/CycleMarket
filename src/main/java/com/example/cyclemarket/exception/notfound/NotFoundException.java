package com.example.cyclemarket.exception.notfound;

import com.example.cyclemarket.exception.ApplicationException;

public class NotFoundException extends ApplicationException {
    public NotFoundException(String message) {
        super(message);
    }
}
