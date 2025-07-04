package com.extrabite.dto;

import com.extrabite.entity.DeliveryType;
import com.extrabite.entity.DonationStatus;
import com.extrabite.entity.FoodType;
import lombok.Data;

import java.time.LocalDateTime;

// Donation ka response bhejne ke liye class hai
@Data
public class DonationResponse {
    private Long id;
    private String foodName;
    private String description;
    private String quantity;
    private LocalDateTime expiryDateTime;
    private boolean free;
    private Double price;
    private String location;
    private String geolocation;
    private DeliveryType deliveryType;
    private DonationStatus status;
    private LocalDateTime createdDateTime;
    private Long donorId;
    private String donorName;
    private FoodType foodType;
    private Boolean refrigerationAvailable;
    private Boolean timer;
    private Long countdownTime;
    private String imageUrl; // Optional image URL for the donation
}