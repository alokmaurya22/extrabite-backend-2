package com.extrabite.service.impl;

import com.extrabite.dto.LoginRequest;
import com.extrabite.dto.LoginResponse;
import com.extrabite.dto.RegisterRequest;
import com.extrabite.dto.RegisterResponse;
import com.extrabite.entity.Role;
import com.extrabite.entity.User;
import com.extrabite.repository.UserRepository;
import com.extrabite.service.AuthService;
import com.extrabite.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Implementation of AuthService to handle user registration logic.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public RegisterResponse registerNewUser(RegisterRequest request) {
        String normalizedEmail = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new RuntimeException("Email already in use");
        }

        if (request.getRole() == Role.ADMIN || request.getRole() == Role.VOLUNTEER) {
            throw new RuntimeException("Invalid role for public registration.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .fullName(request.getFullName())
                .email(normalizedEmail)
                .password(encodedPassword)
                .contactNumber(request.getContactNumber())
                .location(request.getLocation())
                .role(request.getRole())
                .registrationDate(LocalDateTime.now())
                .profileActive(true)
                .build();

        User savedUser = userRepository.save(user);

        // Generate token using JwtUtil
        String token = jwtUtil.generateToken(savedUser);

        // Return token with registration response
        return RegisterResponse.builder()
                .id(savedUser.getId())
                .fullName(savedUser.getFullName())
                .email(savedUser.getEmail())
                .contactNumber(savedUser.getContactNumber())
                .location(savedUser.getLocation())
                .role(savedUser.getRole())
                .accessToken(token)
                .message("Registration successful and logged in")
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        // Normalize email
        String email = request.getEmail().trim().toLowerCase();

        // Find user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Check if account is active
        if (!Boolean.TRUE.equals(user.getProfileActive())) {
            throw new RuntimeException("Your account is deactivated.");
        }

        // Check if password matches
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // If valid, generate JWT token
        String token = jwtUtil.generateToken(user);

        //  System.out.println("Looking up email: " + request.getEmail());
        //  System.out.println("User in DB: " + user.getEmail());
        //  System.out.println("Password matches: " + passwordEncoder.matches(request.getPassword(), user.getPassword()));
        // Return token and user info in response
        return LoginResponse.builder()
                .accessToken(token)
                .userId(user.getId())
                .role(user.getRole())
                .message("Login successful")
                .build();
    }
}