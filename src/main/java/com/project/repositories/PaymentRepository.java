package com.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
