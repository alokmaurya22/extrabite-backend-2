package com.extrabite.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// Food request ki details ke liye class hai
@Entity
@Table(name = "food_requests")
@Data
public class FoodRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fulfiller_id")
    private User fulfiller;

    @Column(nullable = false)
    private String foodType;

    private String alternativeFood;

    @Column(nullable = false)
    private Integer numberOfPeople;

    @Column(length = 500)
    private String message;

    @Column(nullable = false)
    private Double offerPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FoodRequestStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(length = 6)
    private String pickupCode;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private LocalDateTime requestExpiryTime;

    @Column(nullable = false)
    private LocalDateTime requestedTime;

    private String location;
    private String geolocation;
    private String foodDescription;
    private String contactNumber;

    private LocalDateTime submittedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        submittedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}