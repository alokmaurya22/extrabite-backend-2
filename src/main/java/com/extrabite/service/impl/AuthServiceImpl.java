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
 * This class handles authentication logic like register and login
 * Yaha pe user register aur login ka kaam hota hai
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    // Repository to access user data
    private final UserRepository userRepository;
    // For encoding and decoding passwords
    private final PasswordEncoder passwordEncoder;
    // For making and reading JWT tokens
    private final JwtUtil jwtUtil;

    /**
     * This method is for registering new user
     */
    @Override
    @Transactional
    public RegisterResponse registerNewUser(RegisterRequest request) {
        // Email ko clean aur lowercase bana rahe hain
        String normalizedEmail = request.getEmail().trim().toLowerCase();

        // Check kar rahe hain ki email already hai ya nahi
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new RuntimeException("Email already in use");
        }

        // Public registration ke liye admin ya volunteer role allowed nahi hai
        if (request.getRole() == Role.ADMIN || request.getRole() == Role.VOLUNTEER) {
            throw new RuntimeException("Invalid role for public registration.");
        }

        // Password ko encode kar rahe hain
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Naya user bana rahe hain
        User user = User.builder()
                .fullName(request.getFullName())
                .email(normalizedEmail)
                .password(encodedPassword)
                .contactNumber(request.getContactNumber())
                .location(request.getLocation())
                .role(request.getRole())
                .registrationDate(LocalDateTime.now())
                .profileActive(true)
                .fssaiLicenseNumber(request.getFssaiLicenseNumber())
                .build();

        // User ko database me save kar rahe hain
        User savedUser = userRepository.save(user);

        // Token bana rahe hain naya user ke liye
        String token = jwtUtil.generateToken(savedUser);

        // Response bana ke return kar rahe hain
        return RegisterResponse.builder()
                .id(savedUser.getId())
                .fullName(savedUser.getFullName())
                .email(savedUser.getEmail())
                .contactNumber(savedUser.getContactNumber())
                .location(savedUser.getLocation())
                .role(savedUser.getRole())
                .fssaiLicenseNumber(savedUser.getFssaiLicenseNumber())
                .accessToken(token)
                .message("Registration successful and logged in")
                .build();
    }

    /**
     * This method is for user login
     */
    @Override
    public LoginResponse login(LoginRequest request) {
        // Email ko clean aur lowercase bana rahe hain
        String email = request.getEmail().trim().toLowerCase();

        // User ko email se dhoond rahe hain
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Check kar rahe hain ki account active hai ya nahi
        if (!Boolean.TRUE.equals(user.getProfileActive())) {
            throw new RuntimeException("Your account is deactivated.");
        }

        // Password match kar rahe hain
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Sab sahi hai toh token bana rahe hain
        String token = jwtUtil.generateToken(user);

        // Response bana ke return kar rahe hain
        return LoginResponse.builder()
                .accessToken(token)
                .userId(user.getId())
                .role(user.getRole())
                .message("Login successful")
                .build();
    }
}