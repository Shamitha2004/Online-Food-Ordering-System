package com.project.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.entities.MenuItems;
import com.project.entities.Orders;
import com.project.repositories.MenuItemRepository;
import com.project.repositories.OrdersRepository;
import com.project.repositories.UserRepository;
import com.project.services.MenuItemService;
import com.project.services.OrderService;
import com.project.dtos.OrderRequestDTO;
import com.project.entities.OrderStatus;


@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrdersRepository ordersRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    
    @PostMapping
    public Orders placeOrder(@RequestBody OrderRequestDTO orderRequest) {
        return orderService.placeOrder(orderRequest);
    }
    
    
    @GetMapping
    public List<Orders> getAllOrders() {
        return orderService.getAllOrders();
    }


    @GetMapping("/{id}")
    public Orders getOrderById(@PathVariable Long id) {
        return ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }
    
 // Update order status safely
    @PutMapping("/{id}/status")
    public ResponseEntity<Orders> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {

        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        // validate allowed transitions
        if (!isValidTransition(order.getStatus(), status)) {
            throw new RuntimeException(
                "Invalid transition from " + order.getStatus() + " to " + status
            );
        }

        order.setStatus(status);
        return ResponseEntity.ok(ordersRepository.save(order));
    }

    private boolean isValidTransition(OrderStatus current, OrderStatus next) {
        return switch (current) {
            case PENDING -> (next == OrderStatus.CONFIRMED || next == OrderStatus.CANCELLED);
            case CONFIRMED -> (next == OrderStatus.OUT_FOR_DELIVERY || next == OrderStatus.CANCELLED);
            case OUT_FOR_DELIVERY -> (next == OrderStatus.DELIVERED || next == OrderStatus.CANCELLED);
            case DELIVERED, CANCELLED -> false;
        };
    }

    
    @PutMapping("/update/{id}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Long id, @RequestBody Orders updatedOrder) {
        return ordersRepository.findById(id)
                .map(existingOrder -> {
                    // Update simple fields
                    if (updatedOrder.getStatus() != null) {
                        existingOrder.setStatus(updatedOrder.getStatus());
                    }
                    if (updatedOrder.getOrderTime() != null) {
                        existingOrder.setOrderTime(updatedOrder.getOrderTime());
                    }

                    // Update User if provided
                    if (updatedOrder.getUser() != null && updatedOrder.getUser().getId() != null) {
                        existingOrder.setUser(
                            userRepository.findById(updatedOrder.getUser().getId())
                                    .orElseThrow(() -> new RuntimeException("User not found with id: " + updatedOrder.getUser().getId()))
                        );
                    }

                    // Update MenuItems if provided
                    if (updatedOrder.getMenuItems() != null && !updatedOrder.getMenuItems().isEmpty()) {
                        List<MenuItems> items = menuItemRepository.findAllById(
                            updatedOrder.getMenuItems()
                                        .stream()
                                        .map(MenuItems::getId)
                                        .toList()
                        );
                        existingOrder.setMenuItems(items);
                    }

                    return ResponseEntity.ok(ordersRepository.save(existingOrder));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        ordersRepository.delete(order);
        return "Order deleted successfully!";
    }
}
