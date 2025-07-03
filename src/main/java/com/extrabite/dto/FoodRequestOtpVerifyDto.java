package com.extrabite.dto;

import lombok.Data;

// Food request OTP verify karne ke liye class hai
@Data
public class FoodRequestOtpVerifyDto {
    private String pickupCode;
}