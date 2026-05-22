package com.example.cyclemarket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CheckoutRequest {
    @NotBlank(message = "Введите ФИО получателя")
    @Size(min = 5, max = 120, message = "ФИО должно содержать от 5 до 120 символов")
    private String recipientFullName;

    @NotBlank(message = "Введите телефон получателя")
    @Size(min = 10, max = 30, message = "Телефон должен содержать от 10 до 30 символов")
    private String recipientPhone;

    @Size(max = 500, message = "Комментарий не должен превышать 500 символов")
    private String comment;
}
