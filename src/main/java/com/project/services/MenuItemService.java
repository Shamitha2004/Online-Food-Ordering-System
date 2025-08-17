package com.project.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.entities.MenuItems;
import com.project.repositories.MenuItemRepository;
import com.project.exceptions.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Service
//public class MenuItemService {
//
//    @Autowired
//    private MenuItemRepository menuItemRepository;
//    
//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
//
//    public MenuItems addMenuItem(MenuItems menuItem) {
//        return menuItemRepository.save(menuItem);
//    }
//
//    public List<MenuItems> getAllMenuItems() {
//        return menuItemRepository.findAll();
//    }
//
//    public MenuItems getMenuItemById(Long id) {
//        return menuItemRepository.findById(id)
//            .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + id));
//    }
//
//    public MenuItems updateMenuItem(Long id, MenuItems menuItem) {
//        MenuItems existing = getMenuItemById(id);
//        existing.setName(menuItem.getName());
//        existing.setPrice(menuItem.getPrice());
//        existing.setDescription(menuItem.getDescription());
//        existing.setCategory(menuItem.getCategory());
//        return menuItemRepository.save(existing);
//    }
//    
//    
//
//    public void deleteMenuItem(Long id) {
//        getMenuItemById(id); // check existence
//        menuItemRepository.deleteById(id);
//    }

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    private static final Logger logger = LoggerFactory.getLogger(MenuItemService.class);

    public MenuItems addMenuItem(MenuItems menuItem) {
        MenuItems saved = menuItemRepository.save(menuItem);
        logger.info("Menu item added: {}", saved.getName());
        return saved;
    }

    public List<MenuItems> getAllMenuItems() {
        logger.info("Fetching all menu items");
        return menuItemRepository.findAll();
    }

    public MenuItems getMenuItemById(Long id) {
        MenuItems item = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + id));
        logger.info("Fetched menu item: {}", item.getName());
        return item;
    }

    public MenuItems updateMenuItem(Long id, MenuItems menuItem) {
        MenuItems existing = getMenuItemById(id);
        existing.setName(menuItem.getName());
        existing.setPrice(menuItem.getPrice());
        existing.setDescription(menuItem.getDescription());
        existing.setCategory(menuItem.getCategory());
        MenuItems updated = menuItemRepository.save(existing);
        logger.info("Menu item updated: {}", updated.getName());
        return updated;
    }

    public void deleteMenuItem(Long id) {
        MenuItems item = getMenuItemById(id); // check existence
        menuItemRepository.deleteById(id);
        logger.info("Menu item deleted: {}", item.getName());
    }
}


