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
 * Controller to handle authentication-related endpoints such as register, login, reset-password, and logout.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final BlacklistedTokenService blacklistedTokenService;

    /**
     * Register a new user (Donor or Receiver)
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        System.out.println(">> /register hit >> Email: " + request.getEmail());
        RegisterResponse response = authService.registerNewUser(request);
        return ResponseEntity.ok(response);
    }

    /**
     * User login
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Reset password using email and contact number verification
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null || !user.getContactNumber().equals(request.getContactNumber())) {
            return ResponseEntity.badRequest().body("Invalid email or contact number.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("Password reset successful. You can now log in with your new password.");
    }

    /**
     * Logout user by blacklisting JWT token
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            blacklistedTokenService.blacklistToken(token); // Save token to DB
            return ResponseEntity.ok("Token has been invalidated");
        }
        return ResponseEntity.badRequest().body("Invalid Authorization header");
    }
}
