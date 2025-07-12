package com.extrabite.dto;

import com.extrabite.entity.DeliveryType;
import com.extrabite.entity.PaymentMethod;
import com.extrabite.entity.FoodType;
import lombok.Data;
import java.time.LocalDateTime;

// Donation request bhejne ke liye class hai
@Data
public class DonationRequest {
    private String foodName;
    private String description;
    private String quantity;
    private LocalDateTime expiryDateTime;
    private boolean free;
    private Double price;
    private String location;
    private String geolocation;
    private DeliveryType deliveryType;
    private PaymentMethod paymentMethod;
    private FoodType foodType;
    private Boolean refrigerationAvailable;
    private Boolean timer;
    private Long countdownTime;
    private String imageUrl; // Optional image URL for the donation
    private LocalDateTime createdDateTime; // Optional createdDateTime for custom creation time
}