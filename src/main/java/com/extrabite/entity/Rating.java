package com.extrabite.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

// Rating ki details ke liye class hai
@Entity
@Table(name = "ratings")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donation_request_id", nullable = false)
    private DonationRequest donationRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rater_id", nullable = false)
    private User rater; // The user giving the rating

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rated_user_id", nullable = false)
    private User ratedUser; // The user being rated

    @Column(nullable = false)
    private Integer rating; // 1-5 stars

    private String comment; // Optional comment

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RatingType ratingType; // DONOR_TO_RECEIVER or RECEIVER_TO_DONOR

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}