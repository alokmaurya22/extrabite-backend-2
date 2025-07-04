package com.extrabite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HungerVsFoodWasteBarChartResponse {
    private int year;
    private int hunger; // million people
    private double foodWaste; // million or billion tonnes
}