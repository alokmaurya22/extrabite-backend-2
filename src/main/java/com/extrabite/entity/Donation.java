package com.extrabite.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

// This class is for donation data
// Yaha pe donation ki saari info store hoti hai
@Entity
@Table(name = "donations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String foodName;

    private String description;

    private String quantity;

    private LocalDateTime expiryDateTime;

    @Column(name = "is_free", nullable = false)
    private boolean free;

    private Double price;

    @Column(nullable = false)
    private String location;

    @Column
    private String geolocation;

    @Enumerated(EnumType.STRING)
    @Column
    private DeliveryType deliveryType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DonationStatus status;

    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id", nullable = false)
    private User donor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FoodType foodType;

    private Boolean refrigerationAvailable;

    private Boolean timer;

    private Long countdownTime;

    @Column(name = "image_url", nullable = true)
    private String imageUrl;
}