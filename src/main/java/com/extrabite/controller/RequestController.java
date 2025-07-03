package com.extrabite.controller;

import com.extrabite.dto.ConfirmPickupRequestDto;
import com.extrabite.dto.RequestResponseDto;
import com.extrabite.dto.PickupCodeResponseDto;
import com.extrabite.entity.PaymentMethod;
import com.extrabite.entity.Donation;
import com.extrabite.repository.DonationRepository;
import com.extrabite.service.RequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// Request related APIs ke liye controller hai
@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;
    private final DonationRepository donationRepository;

    // Any authenticated user can create a request for a donation
    @PostMapping("/create/{donationId}")
    public ResponseEntity<RequestResponseDto> createRequest(@PathVariable Long donationId,
            @RequestBody Map<String, String> payload, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new RuntimeException("Donation not found with id: " + donationId));
        PaymentMethod paymentMethod;
        if (donation.isFree()) {
            paymentMethod = PaymentMethod.NOT_APPLICABLE;
        } else {
            if (!payload.containsKey("paymentMethod")) {
                throw new RuntimeException("paymentMethod is required for non-free donations");
            }
            paymentMethod = PaymentMethod.valueOf(payload.get("paymentMethod").toUpperCase());
        }
        RequestResponseDto response = requestService.createRequest(donationId, userDetails.getUsername(),
                paymentMethod);
        return ResponseEntity.ok(response);
    }

    // The owner of the donation can accept a request
    @PostMapping("/{requestId}/accept")
    public ResponseEntity<RequestResponseDto> acceptRequest(@PathVariable Long requestId,
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        RequestResponseDto response = requestService.acceptRequest(requestId, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    // The owner of the donation can reject a request
    @PostMapping("/{requestId}/reject")
    public ResponseEntity<RequestResponseDto> rejectRequest(@PathVariable Long requestId,
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        RequestResponseDto response = requestService.rejectRequest(requestId, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    // The owner of the donation can confirm the pickup using the OTP
    @PostMapping("/{requestId}/confirm-pickup")
    public ResponseEntity<RequestResponseDto> confirmPickup(@PathVariable Long requestId,
            @Valid @RequestBody ConfirmPickupRequestDto pickupRequest, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        RequestResponseDto response = requestService.confirmPickup(requestId, pickupRequest, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    // Get all requests made by the logged-in user
    @GetMapping("/my-sent-requests")
    public ResponseEntity<List<RequestResponseDto>> getMySentRequests(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<RequestResponseDto> responses = requestService.getMySentRequests(userDetails.getUsername());
        return ResponseEntity.ok(responses);
    }

    // Get all requests received for the logged-in user's donations
    @GetMapping("/my-received-requests")
    public ResponseEntity<List<RequestResponseDto>> getMyReceivedRequests(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<RequestResponseDto> responses = requestService.getMyReceivedRequests(userDetails.getUsername());
        return ResponseEntity.ok(responses);
    }

    // Endpoint for receiver to get their pickup code
    @GetMapping("/{requestId}/pickup-code")
    public ResponseEntity<PickupCodeResponseDto> getPickupCode(@PathVariable Long requestId,
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        PickupCodeResponseDto response = requestService.getPickupCodeForReceiver(requestId, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }
}