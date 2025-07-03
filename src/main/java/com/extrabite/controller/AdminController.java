package com.extrabite.controller;

import com.extrabite.dto.AdminCreationRequest;
import com.extrabite.dto.RegisterResponse;
import com.extrabite.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// Admin related APIs ke liye controller hai
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // Sample endpoint for testing
    @GetMapping
    public ResponseEntity<?> adminHome(Authentication authentication) {
        return ResponseEntity.ok("Hello, Super Admin: " + authentication.getName());
    }

    @GetMapping("/{any}")
    public ResponseEntity<?> adminPath(Authentication authentication) {
        return ResponseEntity.ok("Hello, Super Admin: " + authentication.getName());
    }

    @GetMapping("/hello")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String getAdmin() {
        return "Welcome, SUPER_ADMIN!";
    }

    @PostMapping("/create-admin")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<RegisterResponse> createAdmin(@Valid @RequestBody AdminCreationRequest request) {
        RegisterResponse response = adminService.createAdmin(request);
        return ResponseEntity.ok(response);
    }
}
