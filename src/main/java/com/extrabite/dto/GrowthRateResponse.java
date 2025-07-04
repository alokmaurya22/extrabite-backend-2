package com.extrabite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrowthRateResponse {
    private String type; // hunger or foodWaste
    private String region; // India or Global
    private double cagr; // Compound Annual Growth Rate (%)
}