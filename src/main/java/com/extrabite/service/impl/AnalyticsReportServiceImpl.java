package com.extrabite.service.impl;

import com.extrabite.service.AnalyticsReportService;
import org.springframework.stereotype.Service;
import java.util.*;
import com.extrabite.dto.YearlyDataResponse;
import com.extrabite.dto.GrowthRateResponse;
import com.extrabite.dto.DailyComparisonResponse;
import com.extrabite.dto.FoodWasteSourceBreakdownResponse;
import com.extrabite.dto.StaticsSummaryResponse;
import com.extrabite.dto.HungerVsFoodWasteBarChartResponse;
import com.extrabite.util.ExtrabiteDataEngine;

@Service
public class AnalyticsReportServiceImpl implements AnalyticsReportService {
    // Analytics report se related saari service logic yaha hai

    @Override
    public Map<String, Object> getAdminSummary() {
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalUsers", 0);
        summary.put("totalDonations", 0);
        summary.put("totalFoodRequests", 0);
        summary.put("totalRatings", 0);
        return summary;
    }

    @Override
    public List<Map<String, Object>> getAdminDonationsReport() {
        return Collections.emptyList();
    }

    @Override
    public List<Map<String, Object>> getAdminUsersReport() {
        return Collections.emptyList();
    }

    @Override
    public Map<String, Object> getUserSummary(String userEmail) {
        Map<String, Object> summary = new HashMap<>();
        summary.put("myDonations", 0);
        summary.put("myFoodRequests", 0);
        summary.put("myRatingsGiven", 0);
        summary.put("myRatingsReceived", 0);
        return summary;
    }

    @Override
    public List<Map<String, Object>> getUserDonationsReport(String userEmail) {
        return Collections.emptyList();
    }

    @Override
    public List<Map<String, Object>> getUserRatingsReport(String userEmail) {
        return Collections.emptyList();
    }

    @Override
    public List<Map<String, Object>> getUserFoodRequestsReport(String userEmail) {
        return Collections.emptyList();
    }

    @Override
    public List<Map<String, Object>> getAdminRequestedDonationsReport() {
        // Example stub data
        return Collections.singletonList(Map.of(
                "donationId", 1,
                "foodName", "Rice",
                "requestedBy", "user@example.com",
                "status", "REQUESTED"));
    }

    @Override
    public List<Map<String, Object>> getTopDonors(String location, Boolean available) {
        // Example stub data with filters
        Map<String, Object> donor = new HashMap<>();
        donor.put("userId", 1);
        donor.put("name", "Alice");
        donor.put("donations", 20);
        donor.put("location", location != null ? location : "All");
        donor.put("available", available != null ? available : true);
        return Collections.singletonList(donor);
    }

    // Statics APIs
    @Override
    public List<YearlyDataResponse> getYearlyData(String dataType, String region, int startYear, int endYear) {
        List<YearlyDataResponse> result = new ArrayList<>();
        for (int year = startYear; year <= endYear; year++) {
            Integer hunger = null;
            Double foodWaste = null;
            String foodWasteUnit = null;
            Map<String, Double> breakdown = null;
            if (region.equalsIgnoreCase("India")) {
                if (!ExtrabiteDataEngine.getIndiaHunger().containsKey(year)
                        && !ExtrabiteDataEngine.getIndiaFoodWasteTonnes().containsKey(year))
                    continue;
                if (dataType.equalsIgnoreCase("hunger") || dataType.equalsIgnoreCase("both")) {
                    hunger = ExtrabiteDataEngine.getIndiaHunger().get(year);
                }
                if (dataType.equalsIgnoreCase("foodWaste") || dataType.equalsIgnoreCase("both")) {
                    foodWaste = ExtrabiteDataEngine.getIndiaFoodWasteTonnes().get(year) != null
                            ? ExtrabiteDataEngine.getIndiaFoodWasteTonnes().get(year).doubleValue()
                            : null;
                    foodWasteUnit = "Million Tonnes";
                    if (foodWaste != null) {
                        breakdown = new HashMap<>();
                        for (Map.Entry<String, Double> e : ExtrabiteDataEngine.getFoodWasteSources().entrySet()) {
                            breakdown.put(e.getKey(), Math.round(foodWaste * e.getValue() * 100.0) / 100.0);
                        }
                    }
                }
            } else if (region.equalsIgnoreCase("Global")) {
                if (!ExtrabiteDataEngine.getGlobalHunger().containsKey(year)
                        && !ExtrabiteDataEngine.getGlobalFoodWasteTonnes().containsKey(year))
                    continue;
                if (dataType.equalsIgnoreCase("hunger") || dataType.equalsIgnoreCase("both")) {
                    hunger = ExtrabiteDataEngine.getGlobalHunger().get(year);
                }
                if (dataType.equalsIgnoreCase("foodWaste") || dataType.equalsIgnoreCase("both")) {
                    foodWaste = ExtrabiteDataEngine.getGlobalFoodWasteTonnes().get(year);
                    foodWasteUnit = "Billion Tonnes";
                    if (foodWaste != null) {
                        breakdown = new HashMap<>();
                        for (Map.Entry<String, Double> e : ExtrabiteDataEngine.getFoodWasteSources().entrySet()) {
                            breakdown.put(e.getKey(), Math.round(foodWaste * e.getValue() * 100.0) / 100.0);
                        }
                    }
                }
            }
            result.add(new YearlyDataResponse(year, hunger, foodWaste, foodWasteUnit, breakdown));
        }
        return result;
    }

    @Override
    public GrowthRateResponse getGrowthRate(String type, String region) {
        // CAGR = (End/Start)^(1/years) - 1
        int startYear = 2015, endYear = 2025;
        double start = 0, end = 0;
        if (type.equalsIgnoreCase("hunger")) {
            if (region.equalsIgnoreCase("India")) {
                start = ExtrabiteDataEngine.getIndiaHunger().get(startYear);
                end = ExtrabiteDataEngine.getIndiaHunger().get(endYear);
            } else {
                start = ExtrabiteDataEngine.getGlobalHunger().get(startYear);
                end = ExtrabiteDataEngine.getGlobalHunger().get(endYear);
            }
        } else if (type.equalsIgnoreCase("foodWaste")) {
            if (region.equalsIgnoreCase("India")) {
                start = ExtrabiteDataEngine.getIndiaFoodWasteTonnes().get(startYear);
                end = ExtrabiteDataEngine.getIndiaFoodWasteTonnes().get(endYear);
            } else {
                start = ExtrabiteDataEngine.getGlobalFoodWasteTonnes().get(startYear);
                end = ExtrabiteDataEngine.getGlobalFoodWasteTonnes().get(endYear);
            }
        }
        double cagr = (start > 0 && end > 0) ? (Math.pow(end / start, 1.0 / (endYear - startYear)) - 1) * 100 : 0;
        return new GrowthRateResponse(type, region, Math.round(cagr * 100.0) / 100.0);
    }

    @Override
    public DailyComparisonResponse getDailyComparison(String region, int year) {
        // 0.5 kg food feeds 1 person/day, 1 tonne = 1000 kg = 2000 people/day
        int dailyHungry = 0, peopleFed = 0, shortBy = 0;
        boolean enoughFood = false;
        if (region.equalsIgnoreCase("India")) {
            Integer hunger = ExtrabiteDataEngine.getIndiaHunger().get(year);
            Integer foodWaste = ExtrabiteDataEngine.getIndiaFoodWasteTonnes().get(year);
            if (hunger != null && foodWaste != null) {
                dailyHungry = (int) Math.round(hunger * 1_000_000.0 / 365.0);
                // New calculation based on 1.5 kg per person
                long totalFoodKg = (long) foodWaste * 1_000_000L * 1000L;
                long requiredFoodKg = (long) hunger * 1_000_000L * 15 / 10; // 1.5 kg per person
                long foodRemainingKg = totalFoodKg - requiredFoodKg;
                if (foodRemainingKg >= 0) {
                    peopleFed = (int) (foodRemainingKg / 15 * 10); // (foodRemainingKg / 1.5)
                    enoughFood = true;
                    shortBy = 0;
                } else {
                    peopleFed = 0;
                    enoughFood = false;
                    shortBy = (int) (Math.abs(foodRemainingKg) / 15 * 10); // (abs(foodRemainingKg) / 1.5)
                }
            }
        } else if (region.equalsIgnoreCase("Global")) {
            Integer hunger = ExtrabiteDataEngine.getGlobalHunger().get(year);
            Double foodWaste = ExtrabiteDataEngine.getGlobalFoodWasteTonnes().get(year);
            if (hunger != null && foodWaste != null) {
                dailyHungry = (int) Math.round(hunger * 1_000_000.0 / 365.0);
                // New calculation based on 1.5 kg per person
                long totalFoodKg = (long) (foodWaste * 1_000_000_000L * 1000L);
                long requiredFoodKg = (long) hunger * 1_000_000L * 15 / 10; // 1.5 kg per person
                long foodRemainingKg = totalFoodKg - requiredFoodKg;
                if (foodRemainingKg >= 0) {
                    peopleFed = (int) (foodRemainingKg / 15 * 10); // (foodRemainingKg / 1.5)
                    enoughFood = true;
                    shortBy = 0;
                } else {
                    peopleFed = 0;
                    enoughFood = false;
                    shortBy = (int) (Math.abs(foodRemainingKg) / 15 * 10); // (abs(foodRemainingKg) / 1.5)
                }
            }
        }
        return new DailyComparisonResponse(region, year, dailyHungry, peopleFed, enoughFood, shortBy);
    }

    @Override
    public FoodWasteSourceBreakdownResponse getFoodWasteSourceBreakdown(String region, int year) {
        double totalFoodWaste = 0;
        String unit = null;
        Map<String, Double> breakdown = null;
        if (region.equalsIgnoreCase("India")) {
            Integer foodWaste = ExtrabiteDataEngine.getIndiaFoodWasteTonnes().get(year);
            if (foodWaste != null) {
                totalFoodWaste = foodWaste;
                unit = "Million Tonnes";
                breakdown = new HashMap<>();
                for (Map.Entry<String, Double> e : ExtrabiteDataEngine.getFoodWasteSources().entrySet()) {
                    breakdown.put(e.getKey(), Math.round(totalFoodWaste * e.getValue() * 100.0) / 100.0);
                }
            }
        } else if (region.equalsIgnoreCase("Global")) {
            Double foodWaste = ExtrabiteDataEngine.getGlobalFoodWasteTonnes().get(year);
            if (foodWaste != null) {
                totalFoodWaste = foodWaste;
                unit = "Billion Tonnes";
                breakdown = new HashMap<>();
                for (Map.Entry<String, Double> e : ExtrabiteDataEngine.getFoodWasteSources().entrySet()) {
                    breakdown.put(e.getKey(), Math.round(totalFoodWaste * e.getValue() * 100.0) / 100.0);
                }
            }
        }
        return new FoodWasteSourceBreakdownResponse(region, year, totalFoodWaste, unit, breakdown);
    }

    @Override
    public StaticsSummaryResponse getStaticsSummary(String region, int year) {
        Integer hunger = null;
        Double foodWaste = null;
        Double cagrHunger = null, cagrFoodWaste = null;
        Integer dailyHungry = null, peopleFed = null, shortBy = null;
        Boolean enoughFood = null;
        Map<String, Double> breakdown = null;
        String foodWasteUnit = null;
        if (region.equalsIgnoreCase("India")) {
            hunger = ExtrabiteDataEngine.getIndiaHunger().get(year);
            foodWaste = ExtrabiteDataEngine.getIndiaFoodWasteTonnes().get(year) != null
                    ? ExtrabiteDataEngine.getIndiaFoodWasteTonnes().get(year).doubleValue()
                    : null;
            foodWasteUnit = "Million Tonnes";
        } else if (region.equalsIgnoreCase("Global")) {
            hunger = ExtrabiteDataEngine.getGlobalHunger().get(year);
            foodWaste = ExtrabiteDataEngine.getGlobalFoodWasteTonnes().get(year);
            foodWasteUnit = "Billion Tonnes";
        }
        if (hunger != null && foodWaste != null) {
            // CAGR
            cagrHunger = getGrowthRate("hunger", region).getCagr();
            cagrFoodWaste = getGrowthRate("foodWaste", region).getCagr();
            // Daily comparison
            DailyComparisonResponse daily = getDailyComparison(region, year);
            dailyHungry = daily.getDailyHungry();
            peopleFed = daily.getPeopleFed();
            enoughFood = daily.isEnoughFood();
            shortBy = daily.getShortBy();
            // Breakdown
            FoodWasteSourceBreakdownResponse br = getFoodWasteSourceBreakdown(region, year);
            breakdown = br.getSourceBreakdown();
        }
        return new StaticsSummaryResponse(region, year, hunger, foodWaste, cagrHunger, cagrFoodWaste, dailyHungry,
                peopleFed, enoughFood, shortBy, breakdown, foodWasteUnit);
    }

    @Override
    public List<HungerVsFoodWasteBarChartResponse> getHungerVsFoodWasteBarChart(String region, int startYear,
            int endYear) {
        List<HungerVsFoodWasteBarChartResponse> result = new ArrayList<>();
        for (int year = startYear; year <= endYear; year++) {
            Integer hunger = null;
            Double foodWaste = null;
            if (region.equalsIgnoreCase("India")) {
                hunger = ExtrabiteDataEngine.getIndiaHunger().get(year);
                foodWaste = ExtrabiteDataEngine.getIndiaFoodWasteTonnes().get(year) != null
                        ? ExtrabiteDataEngine.getIndiaFoodWasteTonnes().get(year).doubleValue()
                        : null;
            } else if (region.equalsIgnoreCase("Global")) {
                hunger = ExtrabiteDataEngine.getGlobalHunger().get(year);
                foodWaste = ExtrabiteDataEngine.getGlobalFoodWasteTonnes().get(year);
            }
            if (hunger != null && foodWaste != null) {
                result.add(new HungerVsFoodWasteBarChartResponse(year, hunger, foodWaste));
            }
        }
        return result;
    }
}