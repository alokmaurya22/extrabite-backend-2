package com.extrabite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YearlyDataResponse {
    private int year;
    private Integer hunger; // million people
    private Double foodWaste; // million or billion tonnes
    private String foodWasteUnit;
    private Map<String, Double> foodWasteBreakdown; // source breakdown
}