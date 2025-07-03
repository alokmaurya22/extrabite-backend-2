package com.extrabite.controller;

import com.extrabite.dto.RatingRequest;
import com.extrabite.dto.RatingResponse;
import com.extrabite.dto.UserRatingSummary;
import com.extrabite.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// Rating ke liye controller hai
@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    // Submit a rating for a completed donation request
    @PostMapping("/submit")
    public ResponseEntity<RatingResponse> submitRating(@Valid @RequestBody RatingRequest ratingRequest,
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        RatingResponse response = ratingService.submitRating(ratingRequest, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    // Get all ratings given by the current user
    @GetMapping("/given")
    public ResponseEntity<List<RatingResponse>> getRatingsGivenByUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<RatingResponse> ratings = ratingService.getRatingsGivenByUser(userDetails.getUsername());
        return ResponseEntity.ok(ratings);
    }

    // Get all ratings received by the current user
    @GetMapping("/received")
    public ResponseEntity<List<RatingResponse>> getRatingsReceivedByUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<RatingResponse> ratings = ratingService.getRatingsReceivedByUser(userDetails.getUsername());
        return ResponseEntity.ok(ratings);
    }

    // Get rating summary for a specific user
    @GetMapping("/summary/{userId}")
    public ResponseEntity<UserRatingSummary> getUserRatingSummary(@PathVariable Long userId) {
        UserRatingSummary summary = ratingService.getUserRatingSummary(userId);
        return ResponseEntity.ok(summary);
    }

    // Get rating statistics for the current user
    @GetMapping("/statistics")
    public ResponseEntity<UserRatingSummary> getRatingStatistics(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserRatingSummary statistics = ratingService.getRatingStatistics(userDetails.getUsername());
        return ResponseEntity.ok(statistics);
    }

    // Get all ratings for a specific donation request
    @GetMapping("/donation-request/{donationRequestId}")
    public ResponseEntity<List<RatingResponse>> getRatingsForDonationRequest(@PathVariable Long donationRequestId) {
        List<RatingResponse> ratings = ratingService.getRatingsForDonationRequest(donationRequestId);
        return ResponseEntity.ok(ratings);
    }

    // Check if user can rate a specific donation request
    @GetMapping("/can-rate/{donationRequestId}")
    public ResponseEntity<Map<String, Boolean>> canUserRateDonationRequest(@PathVariable Long donationRequestId,
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boolean canRate = ratingService.canUserRateDonationRequest(donationRequestId, userDetails.getUsername());
        return ResponseEntity.ok(Map.of("canRate", canRate));
    }
}