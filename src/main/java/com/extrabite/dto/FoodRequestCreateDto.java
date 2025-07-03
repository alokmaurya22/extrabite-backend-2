package com.extrabite.dto;

import lombok.Data;
import java.time.LocalDateTime;
import com.extrabite.entity.PaymentMethod;

// Food request create karne ke liye class hai
@Data
public class FoodRequestCreateDto {
    private String foodType;
    private String alternativeFood;
    private Integer numberOfPeople;
    private String message;
    private Double offerPrice;
    private LocalDateTime requestExpiryTime;
    private LocalDateTime requestedTime;
    private String location;
    private String geolocation;
    private String foodDescription;
    private String contactNumber;
    private PaymentMethod paymentMethod;
}