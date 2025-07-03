package com.extrabite.dto;

import lombok.Data;

import jakarta.validation.constraints.*;

@Data
public class RatingRequest {

    // Rating bhejne ke liye class hai
    @NotNull(message = "Donation request ID is required")
    private Long donationRequestId;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    @Size(max = 500, message = "Comment must not exceed 500 characters")
    private String comment;
}