package com.extrabite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyComparisonResponse {
    private String region;
    private int year;
    private int dailyHungry;
    private int peopleFed;
    private boolean enoughFood;
    private int shortBy;
}