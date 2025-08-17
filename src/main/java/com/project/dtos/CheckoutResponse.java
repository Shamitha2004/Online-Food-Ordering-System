package com.project.dtos;

import java.util.List;

public class CheckoutResponse {
    private Long orderId;
    private double totalAmount;
    private List<String> items;

    public CheckoutResponse(Long orderId, double totalAmount, List<String> items) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public Long getOrderId() { return orderId; }
    public double getTotalAmount() { return totalAmount; }
    public List<String> getItems() { return items; }
}
