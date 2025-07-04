package com.extrabite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaticsSummaryResponse {
    private String region;
    private int year;
    private Integer hunger;
    private Double foodWaste;
    private Double cagrHunger;
    private Double cagrFoodWaste;
    private Integer dailyHungry;
    private Integer peopleFed;
    private Boolean enoughFood;
    private Integer shortBy;
    private Map<String, Double> foodWasteSourceBreakdown;
    private String foodWasteUnit;
}