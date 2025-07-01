package com.extrabite.service.impl;

import com.extrabite.dto.AdminCreationRequest;
import com.extrabite.dto.UserProfileResponse;
import com.extrabite.entity.Role;
import com.extrabite.entity.User;
import com.extrabite.repository.UserRepository;
import com.extrabite.service.SuperAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuperAdminServiceImpl implements SuperAdminService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserProfileResponse registerAdmin(AdminCreationRequest request) {
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setContactNumber(request.getContactNumber());
        user.setLocation(request.getLocation());
        user.setRole(Role.ADMIN);
        user.setProfileActive(true);
        User saved = userRepository.save(user);
        return toProfileResponse(saved);
    }

    @Override
    public void blockUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setProfileActive(false);
        userRepository.save(user);
    }

    @Override
    public void unblockUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setProfileActive(true);
        userRepository.save(user);
    }

    @Override
    public UserProfileResponse getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return toProfileResponse(user);
    }

    private UserProfileResponse toProfileResponse(User user) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .contactNumber(user.getContactNumber())
                .location(user.getLocation())
                .role(user.getRole())
                .profileActive(user.getProfileActive())
                .build();
    }
}