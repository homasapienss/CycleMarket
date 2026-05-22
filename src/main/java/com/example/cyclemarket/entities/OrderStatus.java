package com.example.cyclemarket.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    NEW("Создан, ожидает обработки", "status-new"),
    PROCESSING("Обрабатывается", "status-processing"),
    READY("Готов к выдаче", "status-ready"),
//    SHIPPED("Заказ передан в доставку", "status-shipped"),
    COMPLETED("Успешно завершен", "status-completed"),
    CANCELLED("Отменен", "status-cancelled");
    private final String displayName;
    private final String cssClass;
}
