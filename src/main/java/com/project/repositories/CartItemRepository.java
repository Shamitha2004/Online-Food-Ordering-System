package com.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.entities.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {}
