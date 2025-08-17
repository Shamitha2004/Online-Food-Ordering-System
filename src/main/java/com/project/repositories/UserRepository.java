package com.project.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
