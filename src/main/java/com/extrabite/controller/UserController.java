package com.extrabite.controller;

import com.extrabite.dto.UserProfileResponse;
import com.extrabite.dto.UserUpdateRequest;
import com.extrabite.entity.User;
import com.extrabite.repository.UserRepository;
import com.extrabite.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

// User ke liye controller hai
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    // GET /api/user/profile
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getMyProfile(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserProfileResponse userProfile = userService.getUserProfile(userDetails.getUsername());
        return ResponseEntity.ok(userProfile);
    }

    @PutMapping("/update-profile")
    public ResponseEntity<UserProfileResponse> updateMyProfile(@RequestBody UserUpdateRequest request,
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserProfileResponse updatedProfile = userService.updateUserProfile(userDetails.getUsername(), request);
        return ResponseEntity.ok(updatedProfile);
    }

    @GetMapping("/{any}")
    public ResponseEntity<?> getTokenInfo(@PathVariable String any, Authentication authentication) {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Return only id and email in JSON
        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("email", user.getEmail());

        return ResponseEntity.ok(response);
    }

}
