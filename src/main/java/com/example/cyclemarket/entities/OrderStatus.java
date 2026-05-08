package com.example.cyclemarket.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    NEW("Заказ создан, ожидает обработки", "status-new"),
    PROCESSING("Заказ обрабатывается", "status-processing"),
    SHIPPED("Заказ передан в доставку", "status-shipped"),
    COMPLETED("Заказ успешно завершен", "status-completed"),
    CANCELLED("Заказ отменен", "status-cancelled");
    private final String displayName;
    private final String cssClass;
}
