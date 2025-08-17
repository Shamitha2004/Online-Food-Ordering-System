package com.project.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dtos.OrderRequestDTO;
import com.project.entities.MenuItems;
import com.project.entities.Orders;
import com.project.entities.User;
import com.project.repositories.MenuItemRepository;
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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrdersIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;
    
    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;
    private MenuItems testItem;

    @BeforeEach
    void setup() {
        ordersRepository.deleteAll();
        
        // Fetch an existing user from DB or create if none exist
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            testUser = new User();
            testUser.setUsername("IntegrationUser");
            testUser.setPassword("password");
            testUser.setRole("USER");
            testUser = userRepository.save(testUser);
        } else {
            testUser = users.get(0);
        }
    }



    @Test
    void testPlaceOrderWithExistingMenuItems() throws Exception {
        // Fetch all menu items from the database
        List<MenuItems> menuItems = menuItemRepository.findAll();

        if (menuItems.isEmpty()) {
            throw new RuntimeException("No menu items in the database to place an order.");
        }

        // Collect IDs of all existing menu items
        List<Long> menuItemIds = menuItems.stream()
                                          .map(MenuItems::getId)
                                          .toList();

        // Fetch an existing user from DB or create one if none exist
        User testUser = userRepository.findAll().stream().findFirst().orElseGet(() -> {
            User newUser = new User();
            newUser.setUsername("IntegrationUser");
            newUser.setPassword("password");
            newUser.setRole("USER");
            return userRepository.save(newUser);
        });

        // Prepare order request
        OrderRequestDTO orderRequest = new OrderRequestDTO();
        orderRequest.setUserId(testUser.getId());
        orderRequest.setMenuItemIds(menuItemIds);

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk());
    }


}



