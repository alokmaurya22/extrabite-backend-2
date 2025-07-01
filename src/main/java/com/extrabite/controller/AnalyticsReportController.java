package com.extrabite.controller;

import com.extrabite.dto.*;
import com.extrabite.service.AnalyticsReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsReportController {
    private final AnalyticsReportService analyticsReportService;

    // Admin endpoints
    @GetMapping("/admin/summary")
    public ResponseEntity<Map<String, Object>> getAdminSummary() {
        return ResponseEntity.ok(analyticsReportService.getAdminSummary());
    }

    @GetMapping("/admin/donations")
    public ResponseEntity<List<Map<String, Object>>> getAdminDonationsReport() {
        return ResponseEntity.ok(analyticsReportService.getAdminDonationsReport());
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<Map<String, Object>>> getAdminUsersReport() {
        return ResponseEntity.ok(analyticsReportService.getAdminUsersReport());
    }

    @GetMapping("/admin/requested-donations")
    public ResponseEntity<List<Map<String, Object>>> getAdminRequestedDonationsReport() {
        return ResponseEntity.ok(analyticsReportService.getAdminRequestedDonationsReport());
    }

    // User endpoints
    @GetMapping("/user/summary")
    public ResponseEntity<Map<String, Object>> getUserSummary(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(analyticsReportService.getUserSummary(userDetails.getUsername()));
    }

    @GetMapping("/user/donations")
    public ResponseEntity<List<Map<String, Object>>> getUserDonationsReport(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(analyticsReportService.getUserDonationsReport(userDetails.getUsername()));
    }

    @GetMapping("/user/ratings")
    public ResponseEntity<List<Map<String, Object>>> getUserRatingsReport(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(analyticsReportService.getUserRatingsReport(userDetails.getUsername()));
    }

    @GetMapping("/user/food-requests")
    public ResponseEntity<List<Map<String, Object>>> getUserFoodRequestsReport(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(analyticsReportService.getUserFoodRequestsReport(userDetails.getUsername()));
    }

    // Public endpoint for top donors with filters
    @GetMapping("/public/top-donors")
    public ResponseEntity<List<Map<String, Object>>> getTopDonors(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Boolean available) {
        return ResponseEntity.ok(analyticsReportService.getTopDonors(location, available));
    }
}