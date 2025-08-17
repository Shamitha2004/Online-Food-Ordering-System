package com.project.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.project.dtos.FeedbackRequestDTO;
import com.project.entities.Feedback;
import com.project.entities.User;
import com.project.exceptions.ResourceNotFoundException;
import com.project.repositories.feedbackRepository;
import com.project.repositories.UserRepository;

@Service
public class FeedbackService {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackService.class);

    private final feedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    public FeedbackService(feedbackRepository feedbackRepository, UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
    }

    public Feedback addFeedback(FeedbackRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));

        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setMessage(dto.getMessage());
        feedback.setRating(dto.getRating());

        Feedback saved = feedbackRepository.save(feedback);
        logger.info("Feedback added by user {} with rating {}", user.getUsername(), dto.getRating());
        return saved;
    }

    public List<Feedback> getAllFeedback() {
        logger.info("Fetching all feedbacks");
        return feedbackRepository.findAll();
    }

    public List<Feedback> getFeedbackByUser(Long userId) {
        logger.info("Fetching feedbacks for userId {}", userId);
        return feedbackRepository.findAll().stream()
                .filter(f -> f.getUser().getId().equals(userId))
                .toList();
    }
}
