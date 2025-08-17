package com.project.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.dtos.FeedbackRequestDTO;
import com.project.entities.Feedback;
import com.project.services.FeedbackService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/add")
    public ResponseEntity<Feedback> addFeedback(@Valid @RequestBody FeedbackRequestDTO dto) {
        Feedback feedback = feedbackService.addFeedback(dto);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        return ResponseEntity.ok(feedbackService.getAllFeedback());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Feedback>> getFeedbackByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(feedbackService.getFeedbackByUser(userId));
    }
}
