package com.extrabite.dto;

import com.extrabite.entity.FoodRequestStatus;
import com.extrabite.entity.PaymentMethod;
import lombok.Data;
import java.time.LocalDateTime;

// Food request ka response bhejne ke liye class hai
@Data
public class FoodRequestResponseDto {
    private Long id;
    private Long requesterId;
    private String requesterName;
    private String foodType;
    private String alternativeFood;
    private Integer numberOfPeople;
    private String message;
    private Double offerPrice;
    private FoodRequestStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private PaymentMethod paymentMethod;
    private String pickupCode;
    private LocalDateTime requestExpiryTime;
    private LocalDateTime requestedTime;
    private String location;
    private String geolocation;
    private String foodDescription;
    private String contactNumber;
    private LocalDateTime submittedAt;
    private Long fulfillerId;
    private String fulfillerName;
}