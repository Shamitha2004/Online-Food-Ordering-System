package com.project.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.entities.OrderStatus;
import com.project.entities.Orders;
import com.project.entities.User;
import com.project.repositories.OrdersRepository;
import com.project.repositories.UserRepository;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PaymentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Orders testOrder;

    @BeforeEach
    void setup() {
        // Clean tables
        ordersRepository.deleteAll();
        userRepository.deleteAll();

        // Create user
        User user = new User();
        user.setUsername("PaymentUser");
        user.setPassword("password");
        user.setRole("USER");
        user = userRepository.save(user);

        // Create order
        testOrder = new Orders();
        testOrder.setUser(user);
        testOrder.setStatus(OrderStatus.PENDING);
        testOrder = ordersRepository.save(testOrder);
    }

    @Test
    void payAndConfirmOrderIntegrationTest() throws Exception {
        // Call the payment endpoint
        mockMvc.perform(post("/payments/pay/" + testOrder.getId())
                        .param("amount", "100")
                        .param("method", "UPI")
                        .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());

        // Verify that the order status is updated
        Orders updatedOrder = ordersRepository.findById(testOrder.getId()).orElseThrow();
        assertEquals(OrderStatus.OUT_FOR_DELIVERY, updatedOrder.getStatus());

    }
}
