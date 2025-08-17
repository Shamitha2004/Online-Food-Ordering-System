package com.project.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FeedbackRequestDTO {

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotBlank(message = "Message cannot be empty")
    private String message;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot be more than 5")
    private int rating;

    public FeedbackRequestDTO() {}

    public FeedbackRequestDTO(Long userId, String message, int rating) {
        this.userId = userId;
        this.message = message;
        this.rating = rating;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
}
