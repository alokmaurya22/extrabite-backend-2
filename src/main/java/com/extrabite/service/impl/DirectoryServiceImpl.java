package com.extrabite.service.impl;

import com.extrabite.dto.UserProfileResponse;
import com.extrabite.entity.Role;
import com.extrabite.entity.User;
import com.extrabite.repository.UserRepository;
import com.extrabite.service.DirectoryService;
import com.extrabite.service.spec.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Directory se related saari service logic yaha hai
@Service
@RequiredArgsConstructor
public class DirectoryServiceImpl implements DirectoryService {

    private final UserRepository userRepository;
    private final UserSpecification spec;
    private final UserServiceImpl userServiceImpl; // To reuse the conversion logic

    @Override
    public List<UserProfileResponse> searchUsers(Map<String, String> filters) {
        // Start with a blank specification, by default only find active profiles
        Specification<User> finalSpec = Specification.where(spec.isProfileActive(true));

        if (filters.containsKey("role")) {
            try {
                Role role = Role.valueOf(filters.get("role").toUpperCase());
                finalSpec = finalSpec.and(spec.hasRole(role));
            } catch (IllegalArgumentException e) {
                // Ignore invalid role values
            }
        }

        if (filters.containsKey("location")) {
            finalSpec = finalSpec.and(spec.locationContains(filters.get("location")));
        }

        return userRepository.findAll(finalSpec).stream()
                .map(user -> userServiceImpl.getUserProfile(user.getEmail()))
                .collect(Collectors.toList());
    }
}