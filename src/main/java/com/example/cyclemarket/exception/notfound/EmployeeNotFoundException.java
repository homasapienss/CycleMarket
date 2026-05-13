package com.example.cyclemarket.exception.notfound;

import com.example.cyclemarket.exception.ApplicationException;

public class EmployeeNotFoundException extends ApplicationException {
    public EmployeeNotFoundException() {
        super("Работник не найден");
    }
}
