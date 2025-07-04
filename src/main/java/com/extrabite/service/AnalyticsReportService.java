package com.extrabite.service;

import java.util.List;
import java.util.Map;
import com.extrabite.dto.YearlyDataResponse;
import com.extrabite.dto.GrowthRateResponse;
import com.extrabite.dto.DailyComparisonResponse;
import com.extrabite.dto.FoodWasteSourceBreakdownResponse;
import com.extrabite.dto.StaticsSummaryResponse;
import com.extrabite.dto.HungerVsFoodWasteBarChartResponse;

public interface AnalyticsReportService {
    // Admin
    Map<String, Object> getAdminSummary();

    List<Map<String, Object>> getAdminDonationsReport();

    List<Map<String, Object>> getAdminUsersReport();

    List<Map<String, Object>> getAdminRequestedDonationsReport();

    List<Map<String, Object>> getTopDonors(String location, Boolean available);

    // User
    Map<String, Object> getUserSummary(String userEmail);

    List<Map<String, Object>> getUserDonationsReport(String userEmail);

    List<Map<String, Object>> getUserRatingsReport(String userEmail);

    List<Map<String, Object>> getUserFoodRequestsReport(String userEmail);

    // Statics APIs
    List<YearlyDataResponse> getYearlyData(String dataType, String region, int startYear, int endYear);

    GrowthRateResponse getGrowthRate(String type, String region);

    DailyComparisonResponse getDailyComparison(String region, int year);

    FoodWasteSourceBreakdownResponse getFoodWasteSourceBreakdown(String region, int year);

    StaticsSummaryResponse getStaticsSummary(String region, int year);

    List<HungerVsFoodWasteBarChartResponse> getHungerVsFoodWasteBarChart(String region, int startYear, int endYear);
}