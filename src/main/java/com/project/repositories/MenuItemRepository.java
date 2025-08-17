package com.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.entities.MenuItems;

public interface MenuItemRepository extends JpaRepository<MenuItems, Long> {
}
