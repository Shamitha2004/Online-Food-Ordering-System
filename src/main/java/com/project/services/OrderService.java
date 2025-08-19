package com.project.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entities.MenuItems;
import com.project.entities.Orders;
import com.project.entities.Payment;
import com.project.entities.PaymentStatus;
import com.project.entities.User;
import com.project.repositories.MenuItemRepository;
import com.project.repositories.OrdersRepository;
import com.project.repositories.PaymentRepository;
import com.project.repositories.UserRepository;
import com.project.dtos.OrderRequestDTO;
import com.project.exceptions.ResourceNotFoundException;
import com.project.entities.OrderStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class OrderService {
	
	 private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
	 
    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;
    
    @Autowired
    private PaymentRepository paymentRepository;



    
    public Orders placeOrder(OrderRequestDTO orderRequest) {
        try {
            User user = userRepository.findById(orderRequest.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            List<MenuItems> items = menuItemRepository.findAllById(orderRequest.getMenuItemIds());

            Orders order = new Orders();
            order.setUser(user);
            order.setMenuItems(items);
            order.setStatus(OrderStatus.PENDING); 
            order.setOrderTime(LocalDateTime.now());

            Orders savedOrder = ordersRepository.save(order);
            logger.info("Order placed successfully: {}", savedOrder.getId());
            return savedOrder;

        } catch (Exception e) {
            logger.error("Error placing order: {}", e.getMessage());
            throw e;
        }
    }

    
    public Orders updateOrderStatus(Long orderId, OrderStatus status) {
        try {
            Orders order = ordersRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            order.setStatus(status);
            Orders updatedOrder = ordersRepository.save(order);
            logger.info("Order {} status updated to {}", orderId, status);
            return updatedOrder;
        } catch (Exception e) {
            logger.error("Error updating status for order {}: {}", orderId, e.getMessage());
            throw e;
        }
    }
    public void processPaymentAndDelivery(Long orderId, double amount, String method) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Simulate payment success/failure
        PaymentStatus simulatedStatus = Math.random() < 0.8 ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;
        Payment payment = new Payment(order, amount, method, simulatedStatus);
        paymentRepository.save(payment);

        // Update order based on payment
        if(payment.getStatus() == PaymentStatus.SUCCESS){
            order.setStatus(OrderStatus.CONFIRMED);
            ordersRepository.save(order);

            // Automatically move to OUT_FOR_DELIVERY
            order.setStatus(OrderStatus.OUT_FOR_DELIVERY);
            ordersRepository.save(order);
        } else {
            order.setStatus(OrderStatus.PENDING); 
            ordersRepository.save(order);
        }
    }


    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }
    
    
}


