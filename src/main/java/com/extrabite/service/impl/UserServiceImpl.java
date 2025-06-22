package com.extrabite.service.impl;

import com.extrabite.dto.UserProfileResponse;
import com.extrabite.dto.UserUpdateRequest;
import com.extrabite.entity.User;
import com.extrabite.entity.UserData;
import com.extrabite.repository.DonationRepository;
import com.extrabite.repository.UserDataRepository;
import com.extrabite.repository.UserRepository;
import com.extrabite.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDataRepository userDataRepository;

    @Override
    public UserProfileResponse getUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        UserData userData = user.getUserData();

        // FIX: Lazily create UserData if it doesn't exist for older accounts
        if (userData == null) {
            userData = new UserData();
            userData.setUser(user);
            userData = userDataRepository.save(userData);
            user.setUserData(userData);
        }

        UserProfileResponse.UserDataDto userDataDto = UserProfileResponse.UserDataDto.builder()
                .address(userData.getAddress())
                .alternateContact(userData.getAlternateContact())
                .displayPictureUrl(userData.getDisplayPictureUrl())
                .donationCount(userData.getDonationCount())
                .rating(userData.getRating())
                .build();

        return UserProfileResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .contactNumber(user.getContactNumber())
                .location(user.getLocation())
                .role(user.getRole())
                .registrationDate(user.getRegistrationDate())
                .profileActive(user.getProfileActive())
                .userData(userDataDto)
                .build();
    }

    @Override
    @Transactional
    public UserProfileResponse updateUserProfile(String email, UserUpdateRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        UserData userData = user.getUserData();

        // FIX: Ensure UserData exists before trying to update it
        if (userData == null) {
            userData = new UserData();
            userData.setUser(user);
        }

        // Update basic user fields
        if (StringUtils.hasText(request.getFullName())) {
            user.setFullName(request.getFullName());
        }
        if (StringUtils.hasText(request.getContactNumber())) {
            user.setContactNumber(request.getContactNumber());
        }
        if (StringUtils.hasText(request.getLocation())) {
            user.setLocation(request.getLocation());
        }
        if (request.getProfileActive() != null) {
            user.setProfileActive(request.getProfileActive());
        }

        // Update extended user data fields
        if (StringUtils.hasText(request.getAddress())) {
            userData.setAddress(request.getAddress());
        }
        if (StringUtils.hasText(request.getAlternateContact())) {
            userData.setAlternateContact(request.getAlternateContact());
        }
        if (StringUtils.hasText(request.getDisplayPictureUrl())) {
            userData.setDisplayPictureUrl(request.getDisplayPictureUrl());
        }

        user.setUserData(userData); // Ensure the link is set before saving
        userRepository.save(user); // This will also save/update UserData due to cascade

        return getUserProfile(user.getEmail());
    }
}