package com.extrabite.service.impl;

import com.extrabite.dto.RatingRequest;
import com.extrabite.dto.RatingResponse;
import com.extrabite.dto.UserRatingSummary;
import com.extrabite.entity.*;
import com.extrabite.repository.DonationRequestRepository;
import com.extrabite.repository.RatingRepository;
import com.extrabite.repository.UserDataRepository;
import com.extrabite.repository.UserRepository;
import com.extrabite.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final DonationRequestRepository donationRequestRepository;
    private final UserRepository userRepository;
    private final UserDataRepository userDataRepository;

    @Override
    @Transactional
    public RatingResponse submitRating(RatingRequest ratingRequest, String raterEmail) {
        // Find the donation request
        DonationRequest donationRequest = donationRequestRepository.findById(ratingRequest.getDonationRequestId())
                .orElseThrow(() -> new RuntimeException("Donation request not found"));

        // Find the rater
        User rater = userRepository.findByEmail(raterEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate that the donation request is completed
        if (donationRequest.getStatus() != RequestStatus.COMPLETED) {
            throw new RuntimeException("Can only rate completed donation requests");
        }

        // Validate that the rater is either the donor or receiver
        if (!donationRequest.getDonor().equals(rater) && !donationRequest.getReceiver().equals(rater)) {
            throw new RuntimeException("You can only rate donation requests you participated in");
        }

        // Check if user has already rated this donation request
        if (ratingRepository.existsByDonationRequestIdAndRaterId(ratingRequest.getDonationRequestId(), rater.getId())) {
            throw new RuntimeException("You have already rated this donation request");
        }

        // Determine the rated user and rating type
        User ratedUser;
        RatingType ratingType;

        if (donationRequest.getDonor().equals(rater)) {
            // Donor is rating the receiver
            ratedUser = donationRequest.getReceiver();
            ratingType = RatingType.DONOR_TO_RECEIVER;
        } else {
            // Receiver is rating the donor
            ratedUser = donationRequest.getDonor();
            ratingType = RatingType.RECEIVER_TO_DONOR;
        }

        // Create and save the rating
        Rating rating = Rating.builder()
                .donationRequest(donationRequest)
                .rater(rater)
                .ratedUser(ratedUser)
                .rating(ratingRequest.getRating())
                .comment(ratingRequest.getComment())
                .ratingType(ratingType)
                .build();

        Rating savedRating = ratingRepository.save(rating);

        // Update the rated user's average rating
        updateUserAverageRating(ratedUser);

        return convertToResponse(savedRating);
    }

    @Override
    public List<RatingResponse> getRatingsGivenByUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ratingRepository.findByRater(user).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RatingResponse> getRatingsReceivedByUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ratingRepository.findByRatedUser(user).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserRatingSummary getUserRatingSummary(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return buildUserRatingSummary(user);
    }

    @Override
    public List<RatingResponse> getRatingsForDonationRequest(Long donationRequestId) {
        return ratingRepository.findByDonationRequestId(donationRequestId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean canUserRateDonationRequest(Long donationRequestId, String userEmail) {
        try {
            DonationRequest donationRequest = donationRequestRepository.findById(donationRequestId)
                    .orElseThrow(() -> new RuntimeException("Donation request not found"));

            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Check if donation request is completed
            if (donationRequest.getStatus() != RequestStatus.COMPLETED) {
                return false;
            }

            // Check if user participated in the donation request
            if (!donationRequest.getDonor().equals(user) && !donationRequest.getReceiver().equals(user)) {
                return false;
            }

            // Check if user has already rated
            return !ratingRepository.existsByDonationRequestIdAndRaterId(donationRequestId, user.getId());

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserRatingSummary getRatingStatistics(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return buildUserRatingSummary(user);
    }

    private void updateUserAverageRating(User user) {
        Double averageRating = ratingRepository.getAverageRatingByUser(user);
        Long ratingCount = ratingRepository.getRatingCountByUser(user);

        UserData userData = user.getUserData();
        userData.setRating(averageRating != null ? averageRating : 0.0);
        userDataRepository.save(userData);
    }

    private UserRatingSummary buildUserRatingSummary(User user) {
        List<Rating> ratings = ratingRepository.findByRatedUser(user);

        UserRatingSummary summary = new UserRatingSummary();
        summary.setUserId(user.getId());
        summary.setUserName(user.getFullName());

        if (ratings.isEmpty()) {
            summary.setAverageRating(0.0);
            summary.setTotalRatings(0L);
            summary.setFiveStarRatings(0L);
            summary.setFourStarRatings(0L);
            summary.setThreeStarRatings(0L);
            summary.setTwoStarRatings(0L);
            summary.setOneStarRatings(0L);
            return summary;
        }

        double averageRating = ratings.stream()
                .mapToInt(Rating::getRating)
                .average()
                .orElse(0.0);

        long fiveStar = ratings.stream().filter(r -> r.getRating() == 5).count();
        long fourStar = ratings.stream().filter(r -> r.getRating() == 4).count();
        long threeStar = ratings.stream().filter(r -> r.getRating() == 3).count();
        long twoStar = ratings.stream().filter(r -> r.getRating() == 2).count();
        long oneStar = ratings.stream().filter(r -> r.getRating() == 1).count();

        summary.setAverageRating(Math.round(averageRating * 100.0) / 100.0); // Round to 2 decimal places
        summary.setTotalRatings((long) ratings.size());
        summary.setFiveStarRatings(fiveStar);
        summary.setFourStarRatings(fourStar);
        summary.setThreeStarRatings(threeStar);
        summary.setTwoStarRatings(twoStar);
        summary.setOneStarRatings(oneStar);

        return summary;
    }

    private RatingResponse convertToResponse(Rating rating) {
        RatingResponse response = new RatingResponse();
        response.setId(rating.getId());
        response.setDonationRequestId(rating.getDonationRequest().getId());
        response.setRaterId(rating.getRater().getId());
        response.setRaterName(rating.getRater().getFullName());
        response.setRatedUserId(rating.getRatedUser().getId());
        response.setRatedUserName(rating.getRatedUser().getFullName());
        response.setRating(rating.getRating());
        response.setComment(rating.getComment());
        response.setRatingType(rating.getRatingType());
        response.setCreatedAt(rating.getCreatedAt());
        return response;
    }
}