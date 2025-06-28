package com.extrabite.service;

import com.extrabite.dto.RatingRequest;
import com.extrabite.dto.RatingResponse;
import com.extrabite.dto.UserRatingSummary;

import java.util.List;

public interface RatingService {

    // Submit a rating for a completed donation request
    RatingResponse submitRating(RatingRequest ratingRequest, String raterEmail);

    // Get all ratings given by a user
    List<RatingResponse> getRatingsGivenByUser(String userEmail);

    // Get all ratings received by a user
    List<RatingResponse> getRatingsReceivedByUser(String userEmail);

    // Get rating summary for a user
    UserRatingSummary getUserRatingSummary(Long userId);

    // Get all ratings for a specific donation request
    List<RatingResponse> getRatingsForDonationRequest(Long donationRequestId);

    // Check if user can rate a specific donation request
    boolean canUserRateDonationRequest(Long donationRequestId, String userEmail);

    // Get rating statistics for a user
    UserRatingSummary getRatingStatistics(String userEmail);
}