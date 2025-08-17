package com.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.entities.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
