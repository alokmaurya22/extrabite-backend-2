package com.extrabite.controller;

import com.extrabite.dto.AdminCreationRequest;
import com.extrabite.dto.UserProfileResponse;
import com.extrabite.service.SuperAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Super admin ke liye controller hai
@RestController
@RequestMapping("/api/superadmin")
@RequiredArgsConstructor
public class SuperAdminController {
    private final SuperAdminService superAdminService;

    // Register a new admin
    @PostMapping("/register-admin")
    public ResponseEntity<UserProfileResponse> registerAdmin(@RequestBody AdminCreationRequest request) {
        return ResponseEntity.ok(superAdminService.registerAdmin(request));
    }

    // Block a user by userId
    @PostMapping("/block-user/{userId}")
    public ResponseEntity<String> blockUser(@PathVariable Long userId) {
        superAdminService.blockUser(userId);
        return ResponseEntity.ok("User blocked successfully");
    }

    // Unblock a user by userId
    @PostMapping("/unblock-user/{userId}")
    public ResponseEntity<String> unblockUser(@PathVariable Long userId) {
        superAdminService.unblockUser(userId);
        return ResponseEntity.ok("User unblocked successfully");
    }

    // Get user profile by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(superAdminService.getUserById(userId));
    }
}