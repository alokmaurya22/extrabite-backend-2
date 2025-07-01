package com.extrabite.service;

import java.util.List;
import java.util.Map;

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
}