package com.extrabite.service.impl;

import com.extrabite.service.AnalyticsReportService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AnalyticsReportServiceImpl implements AnalyticsReportService {
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
}