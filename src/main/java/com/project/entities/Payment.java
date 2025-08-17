package com.project.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;


    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String method; // e.g., "CREDIT_CARD", "UPI", "CASH"

    @Column(nullable = false)
    private boolean paid = false;

    private LocalDateTime paymentTime;

    // Constructors
    public Payment() {}

    public Payment(Orders order, double amount, String method) {
        this.order = order;
        this.amount = amount;
        this.method = method;
        this.paid = true;
        this.paymentTime = LocalDateTime.now();
    }
    
    
    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING; // default PENDING

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Payment(Orders order, double amount, String method, PaymentStatus status) {
        this.order = order;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.paid = status == PaymentStatus.SUCCESS;
        this.paymentTime = LocalDateTime.now();
    }
    // Getters & Setters
    public Long getId() { return id; }
    public Orders getOrder() { return order; }
    public void setOrder(Orders order) { this.order = order; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public boolean isPaid() { return paid; }
    public void setPaid(boolean paid) { this.paid = paid; }
    public LocalDateTime getPaymentTime() { return paymentTime; }
    public void setPaymentTime(LocalDateTime paymentTime) { this.paymentTime = paymentTime; }
}
