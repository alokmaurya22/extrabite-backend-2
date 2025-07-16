package com.extrabite.dto;

import com.extrabite.entity.PaymentMethod;
import com.extrabite.entity.RequestStatus;
import com.extrabite.entity.FoodType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestResponseDto {
    private Long id;
    private Long donationId;
    private String foodName;
    private Long receiverId;
    private String receiverName;
    private Long donorId;
    private String donorName;
    private RequestStatus status;
    private PaymentMethod paymentMethod;
    private String pickupCode;
    private LocalDateTime requestDate;
    private LocalDateTime lastUpdateDate;
    private FoodType foodType;
    private Boolean refrigerationAvailable;
    private Boolean timer;
    private Long countdownTime;
    private Boolean free;
    private Double price;
    private String imageUrl;
    private String location; // Added for donation location
    private String geolocation; // Added for donation geolocation
}