package com.example.cyclemarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String ERROR_ATTRIBUTE = "error_message";

    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String orderNotFound(Model model, Exception exception) {
        model.addAttribute(ERROR_ATTRIBUTE, exception.getMessage());
        return "error-page";
    }
}
