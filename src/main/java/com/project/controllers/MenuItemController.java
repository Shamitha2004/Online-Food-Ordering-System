package com.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.entities.MenuItems;
import com.project.services.MenuItemService;

@RestController
@RequestMapping("/menu")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping("/add")
    public ResponseEntity<List<MenuItems>> addMenuItems(@RequestBody List<MenuItems> menuItems) {
        List<MenuItems> savedItems = menuItems.stream()
            .map(menuItemService::addMenuItem)
            .toList();
        return ResponseEntity.ok(savedItems);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MenuItems>> getAllMenuItems() {
        return ResponseEntity.ok(menuItemService.getAllMenuItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItems> getMenuItem(@PathVariable Long id) {
        return ResponseEntity.ok(menuItemService.getMenuItemById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MenuItems> updateMenuItem(@PathVariable Long id, @RequestBody MenuItems menuItem) {
        return ResponseEntity.ok(menuItemService.updateMenuItem(id, menuItem));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }
}
