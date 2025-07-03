package com.extrabite.repository;

import com.extrabite.entity.Rating;
import com.extrabite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Rating ke liye repository hai
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    // Find all ratings given by a specific user
    List<Rating> findByRater(User rater);

    // Find all ratings received by a specific user
    List<Rating> findByRatedUser(User ratedUser);

    // Find rating by donation request and rater
    Optional<Rating> findByDonationRequestIdAndRaterId(Long donationRequestId, Long raterId);

    // Find all ratings for a specific donation request
    List<Rating> findByDonationRequestId(Long donationRequestId);

    // Check if a user has already rated for a specific donation request
    boolean existsByDonationRequestIdAndRaterId(Long donationRequestId, Long raterId);

    // Get average rating for a user
    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.ratedUser = :user")
    Double getAverageRatingByUser(@Param("user") User user);

    // Get count of ratings for a user
    @Query("SELECT COUNT(r) FROM Rating r WHERE r.ratedUser = :user")
    Long getRatingCountByUser(@Param("user") User user);
}