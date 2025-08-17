package com.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entities.Feedback;
import com.project.entities.MenuItems;

public interface feedbackRepository extends JpaRepository<Feedback, Long> {
	
	
}
