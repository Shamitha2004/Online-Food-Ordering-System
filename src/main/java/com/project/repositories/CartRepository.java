package com.project.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.entities.Cart;
import com.project.entities.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
