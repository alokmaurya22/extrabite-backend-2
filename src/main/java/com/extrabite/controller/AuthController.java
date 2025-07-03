package com.extrabite.controller;

import com.extrabite.dto.*;
import com.extrabite.entity.User;
import com.extrabite.repository.UserRepository;
import com.extrabite.service.AuthService;
import com.extrabite.service.BlacklistedTokenService;
import com.extrabite.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * This controller is for authentication related APIs
 * Yaha pe login, register, password reset jaise kaam hote hain
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    // This is the main class for authentication endpoints
    private final AuthService authService;
    // Repository to access user data from database
    private final UserRepository userRepository;
    // For encoding and decoding passwords
    private final PasswordEncoder passwordEncoder;
    // For handling JWT tokens
    private final JwtUtil jwtUtil;
    // Service to handle blacklisted tokens (logout)
    private final BlacklistedTokenService blacklistedTokenService;

    // This API is for registering new user (donor ya receiver)
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        System.out.println(">> /register hit >> Email: " + request.getEmail());
        RegisterResponse response = authService.registerNewUser(request);
        return ResponseEntity.ok(response);
    }

    // This API is for user login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    // This API is for resetting password using email and contact number
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ForgotPasswordRequest request) {
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null || !user.getContactNumber().equals(request.getContactNumber())) {
            return ResponseEntity.badRequest().body("Invalid email or contact number.");
        }

        // Set new password after encoding
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        // Save updated user in database
        userRepository.save(user);

        return ResponseEntity.ok("Password reset successful. You can now log in with your new password.");
    }

    // This API is for logging out user by blacklisting JWT token
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        // Check if header has Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            // Remove token by adding to blacklist
            blacklistedTokenService.blacklistToken(token); // Save token to DB
            return ResponseEntity.ok("Token has been invalidated");
        }
        return ResponseEntity.badRequest().body("Invalid Authorization header");
    }
}
