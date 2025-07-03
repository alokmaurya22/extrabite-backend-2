package com.extrabite.controller;

import com.extrabite.dto.FoodRequestCreateDto;
import com.extrabite.dto.FoodRequestResponseDto;
import com.extrabite.dto.FoodRequestSelectPaymentDto;
import com.extrabite.dto.FoodRequestOtpVerifyDto;
import com.extrabite.service.FoodRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Food request ke liye controller hai
@RestController
@RequestMapping("/api/food-requests")
@RequiredArgsConstructor
public class FoodRequestController {
    private final FoodRequestService foodRequestService;

    @PostMapping("/create")
    public ResponseEntity<FoodRequestResponseDto> create(@RequestBody FoodRequestCreateDto dto,
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        FoodRequestResponseDto response = foodRequestService.createFoodRequest(dto, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/open")
    public ResponseEntity<List<FoodRequestResponseDto>> getAllOpen() {
        return ResponseEntity.ok(foodRequestService.getAllOpenRequests());
    }

    @GetMapping("/my")
    public ResponseEntity<List<FoodRequestResponseDto>> getMyRequests(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(foodRequestService.getMyRequests(userDetails.getUsername()));
    }

    @PostMapping("/{requestId}/offer")
    public ResponseEntity<FoodRequestResponseDto> offerToFulfill(@PathVariable Long requestId,
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        FoodRequestResponseDto response = foodRequestService.offerToFulfill(requestId, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{requestId}/accept-offer")
    public ResponseEntity<FoodRequestResponseDto> acceptOffer(@PathVariable Long requestId,
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        FoodRequestResponseDto response = foodRequestService.acceptOffer(requestId, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{requestId}/complete")
    public ResponseEntity<FoodRequestResponseDto> complete(@PathVariable Long requestId,
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        FoodRequestResponseDto response = foodRequestService.completeRequest(requestId, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{requestId}/cancel")
    public ResponseEntity<FoodRequestResponseDto> cancel(@PathVariable Long requestId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        FoodRequestResponseDto response = foodRequestService.cancelRequest(requestId, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{requestId}/verify-otp")
    public ResponseEntity<FoodRequestResponseDto> verifyOtp(@PathVariable Long requestId,
            @RequestBody FoodRequestOtpVerifyDto dto, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        FoodRequestResponseDto response = foodRequestService.verifyOtpAndComplete(requestId, userDetails.getUsername(),
                dto);
        return ResponseEntity.ok(response);
    }

    // Endpoint for requester to get their pickup code
    @GetMapping("/{requestId}/pickup-code")
    public ResponseEntity<String> getPickupCode(@PathVariable Long requestId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String pickupCode = foodRequestService.getPickupCodeForRequester(requestId, userDetails.getUsername());
        return ResponseEntity.ok(pickupCode);
    }
}