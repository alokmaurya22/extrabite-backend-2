package com.extrabite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodWasteSourceBreakdownResponse {
    private String region;
    private int year;
    private double totalFoodWaste;
    private String unit;
    private Map<String, Double> sourceBreakdown;
}