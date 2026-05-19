package com.example.cyclemarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String ERROR_ATTRIBUTE = "error_message";

    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String getErrorPage(Model model, Exception exception) {
        model.addAttribute(ERROR_ATTRIBUTE, exception.getMessage());
        return "error-page";
    }

    @ExceptionHandler(StockNotFoundException.class)
    public String stockNotFound(RedirectAttributes redirectAttributes, StockNotFoundException exception) {
        redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
        return "redirect:/cart";
    }

    @ExceptionHandler(NotEnoughStockException.class)
    public String notEnoughStock(RedirectAttributes redirectAttributes, NotEnoughStockException exception) {
        redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
        return "redirect:/cart";
    }
}
