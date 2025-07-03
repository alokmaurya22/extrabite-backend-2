package com.extrabite.dto;

import lombok.Data;

// User ki rating ka summary dikhane ke liye class hai
@Data
public class UserRatingSummary {
    private Long userId;
    private String userName;
    private Double averageRating;
    private Long totalRatings;
    private Long fiveStarRatings;
    private Long fourStarRatings;
    private Long threeStarRatings;
    private Long twoStarRatings;
    private Long oneStarRatings;
}