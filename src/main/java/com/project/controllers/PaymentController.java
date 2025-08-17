package com.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.entities.Orders;
import com.project.entities.Payment;
import com.project.repositories.OrdersRepository;
import com.project.repositories.PaymentRepository;
import com.project.services.OrderService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private PaymentRepository paymentRepository;
    

    @Autowired
    private OrderService orderService;
    
    @PostMapping("/pay/{orderId}")
    public ResponseEntity<Orders> payAndDeliver(
            @PathVariable Long orderId,
            @RequestParam double amount,
            @RequestParam String method) {

        orderService.processPaymentAndDelivery(orderId, amount, method);
        Orders updatedOrder = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return ResponseEntity.ok(updatedOrder);
    }


    // Make a payment
    @PostMapping("/{orderId}")
    public ResponseEntity<Payment> makePayment(
            @PathVariable Long orderId,
            @RequestParam double amount,
            @RequestParam String method) {

        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        Payment payment = new Payment(order, amount, method);
        paymentRepository.save(payment);

        return ResponseEntity.ok(payment);
    }
}
