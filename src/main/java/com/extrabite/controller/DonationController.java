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

@RestController
@RequestMapping("/api/donations")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @PostMapping
    public ResponseEntity<DonationResponse> createDonation(@RequestBody DonationRequest donationRequest,
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        DonationResponse createdDonation = donationService.createDonation(donationRequest, userDetails.getUsername());
        return new ResponseEntity<>(createdDonation, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationResponse> getDonationById(@PathVariable Long id) {
        DonationResponse donation = donationService.getDonationById(id);
        return ResponseEntity.ok(donation);
    }

    @GetMapping("/public")
    public ResponseEntity<List<DonationResponse>> getPublicDonations() {
        List<DonationResponse> donations = donationService.getPublicDonations();
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<DonationResponse>> getAllDonations() {
        List<DonationResponse> donations = donationService.getAllDonations();
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/my-donations")
    public ResponseEntity<List<DonationResponse>> getMyDonations(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<DonationResponse> donations = donationService.getDonationsByDonor(userDetails.getUsername());
        return ResponseEntity.ok(donations);
    }

    @PutMapping("/update_donation/{id}")
    public ResponseEntity<DonationResponse> updateDonation(@PathVariable Long id,
            @RequestBody DonationRequest donationRequest, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        DonationResponse updatedDonation = donationService.updateDonation(id, donationRequest,
                userDetails.getUsername());
        return ResponseEntity.ok(updatedDonation);
    }

    @DeleteMapping("/delete-donation/{id}")
    public ResponseEntity<Void> deleteDonation(@PathVariable Long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        donationService.deleteDonation(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}