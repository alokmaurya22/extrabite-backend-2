package com.extrabite.controller;

import com.extrabite.dto.DonationResponse;
import com.extrabite.service.BrowseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/browse")
@RequiredArgsConstructor
public class BrowseController {

    private final BrowseService browseService;

    @GetMapping("/donations")
    public ResponseEntity<List<DonationResponse>> getAllAvailableDonations() {
        List<DonationResponse> donations = browseService.getAllAvailableDonations();
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/donations/search")
    public ResponseEntity<List<DonationResponse>> searchDonations(@RequestParam Map<String, String> allParams) {
        List<DonationResponse> donations = browseService.searchDonations(allParams);
        return ResponseEntity.ok(donations);
    }
}