package com.project.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.entities.MenuItems;
import com.project.repositories.MenuItemRepository;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MenuIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllMenuItems() throws Exception {
        List<MenuItems> menuItems = menuItemRepository.findAll();

        if (menuItems.isEmpty()) {
            throw new RuntimeException("No menu items in the database to test.");
        }

        mockMvc.perform(get("/menu/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(menuItems.get(0).getName()));
    }

    @Test
    void testUpdateAllMenuItems() throws Exception {
        List<MenuItems> menuItems = menuItemRepository.findAll();

        if (menuItems.isEmpty()) {
            throw new RuntimeException("No menu items in the database to update.");
        }

        // Pick the first one for update
        MenuItems itemToUpdate = menuItems.get(0);
        int oldPrice = (int) itemToUpdate.getPrice();
        itemToUpdate.setPrice(oldPrice + 50);

        mockMvc.perform(put("/menu/update/" + itemToUpdate.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemToUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(itemToUpdate.getPrice()));
    }

    @Test
    void testDeleteAllMenuItems() throws Exception {
        List<MenuItems> menuItems = menuItemRepository.findAll();

        if (menuItems.isEmpty()) {
            throw new RuntimeException("No menu items in the database to delete.");
        }

        // Delete the first menu item
        MenuItems itemToDelete = menuItems.get(0);

        mockMvc.perform(delete("/menu/delete/" + itemToDelete.getId()))
                .andExpect(status().isNoContent());
    }
}
