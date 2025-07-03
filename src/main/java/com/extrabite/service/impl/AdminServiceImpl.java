package com.extrabite.service.impl;

import com.extrabite.dto.AdminCreationRequest;
import com.extrabite.dto.RegisterResponse;
import com.extrabite.entity.ModulePermission;
import com.extrabite.entity.Role;
import com.extrabite.entity.User;
import com.extrabite.repository.ModulePermissionRepository;
import com.extrabite.repository.UserRepository;
import com.extrabite.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

// Admin se related saari service logic yaha hai
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final ModulePermissionRepository modulePermissionRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public RegisterResponse createAdmin(AdminCreationRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use.");
        }

        User admin = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .contactNumber(request.getContactNumber())
                .location(request.getLocation())
                .role(Role.ADMIN)
                .registrationDate(LocalDateTime.now())
                .profileActive(true)
                .build();

        Set<ModulePermission> permissions = new HashSet<>();
        if (request.getModules() != null) {
            for (String moduleName : request.getModules()) {
                ModulePermission permission = modulePermissionRepository.findByModuleName(moduleName)
                        .orElseGet(() -> modulePermissionRepository.save(new ModulePermission(moduleName)));
                permissions.add(permission);
            }
        }
        admin.setPermissions(permissions);

        User savedAdmin = userRepository.save(admin);

        return RegisterResponse.builder()
                .id(savedAdmin.getId())
                .fullName(savedAdmin.getFullName())
                .email(savedAdmin.getEmail())
                .role(savedAdmin.getRole())
                .message("Admin user created successfully.")
                .build();
    }
}