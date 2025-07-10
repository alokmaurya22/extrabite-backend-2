package com.extrabite.controller;

import com.extrabite.dto.DonationRequest;
import com.extrabite.dto.DonationResponse;
import com.extrabite.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This controller is for donation related APIs
// Yaha pe donation create, update, delete, dekhne ka kaam hota hai
@RestController
@RequestMapping("/api/donations")
public class DonationController {

    // Service for donation logic
    @Autowired
    private DonationService donationService;

    // API to create a new donation
    @PostMapping
    public ResponseEntity<DonationResponse> createDonation(@RequestBody DonationRequest donationRequest,
            Authentication authentication) {
        // User ka username nikal rahe hain
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        DonationResponse createdDonation = donationService.createDonation(donationRequest, userDetails.getUsername());
        return new ResponseEntity<>(createdDonation, HttpStatus.CREATED);
    }

    // API to get donation by id
    @GetMapping("/{id}")
    public ResponseEntity<DonationResponse> getDonationById(@PathVariable Long id) {
        DonationResponse donation = donationService.getDonationById(id);
        return ResponseEntity.ok(donation);
    }

    // API to get all donations (admin/superadmin only)
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<DonationResponse>> getAllDonations() {
        List<DonationResponse> donations = donationService.getAllDonations();
        return ResponseEntity.ok(donations);
    }

    // API to get donations of logged-in user
    @GetMapping("/my-donations")
    public ResponseEntity<List<DonationResponse>> getMyDonations(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<DonationResponse> donations = donationService.getDonationsByDonor(userDetails.getUsername());
        return ResponseEntity.ok(donations);
    }

    // API to update a donation
    @PutMapping("/update_donation/{id}")
    public ResponseEntity<DonationResponse> updateDonation(@PathVariable Long id,
            @RequestBody DonationRequest donationRequest, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        DonationResponse updatedDonation = donationService.updateDonation(id, donationRequest,
                userDetails.getUsername());
        return ResponseEntity.ok(updatedDonation);
    }

    // API to delete a donation
    @DeleteMapping("/delete-donation/{id}")
    public ResponseEntity<Void> deleteDonation(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        donationService.deleteDonation(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    // Platform API to reject a donation by ID if timer expired
    @PostMapping("/{id}/reject-by-platform")
    public ResponseEntity<DonationResponse> rejectByPlatform(@PathVariable Long id) {
        DonationResponse response = donationService.rejectByPlatform(id);
        return ResponseEntity.ok(response);
    }

    // Platform API to expire a donation by expiryDateTime
    @PostMapping("/{id}/expire-by-expiry-time")
    public ResponseEntity<DonationResponse> expireByExpiryTime(@PathVariable Long id) {
        DonationResponse response = donationService.expireByExpiryTime(id);
        return ResponseEntity.ok(response);
    }

    // API to change status of all donations with a specific status to a new status
    @PostMapping("/changeTargetedDonationStatus/{targetedStatus}/{requiredStatus}")
    public ResponseEntity<List<DonationResponse>> changeTargetedDonationStatus(
            @PathVariable String targetedStatus,
            @PathVariable String requiredStatus) {
        List<DonationResponse> changedDonations = donationService.changeTargetedDonationStatus(targetedStatus,
                requiredStatus);
        return ResponseEntity.ok(changedDonations);
    }
}