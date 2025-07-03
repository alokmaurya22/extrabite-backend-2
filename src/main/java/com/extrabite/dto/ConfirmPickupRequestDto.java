package com.extrabite.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

// Pickup confirm karne ke liye class hai
@Data
public class ConfirmPickupRequestDto {
    @NotBlank
    @Size(min = 6, max = 6)
    private String pickupCode;
}