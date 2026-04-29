package com.example.cyclemarket.exception.alreadyxists;

import com.example.cyclemarket.exception.ApplicationException;

public class AlreadyExistsException extends ApplicationException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}
