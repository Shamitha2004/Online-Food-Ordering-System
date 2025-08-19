package com.project.entities;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderStatus {
    PENDING,
    CONFIRMED,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED;
    
    @JsonCreator
    public static OrderStatus fromString(String value) {
        return OrderStatus.valueOf(value.toUpperCase());
    }
}
