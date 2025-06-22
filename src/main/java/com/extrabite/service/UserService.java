package com.extrabite.service;

import com.extrabite.dto.UserProfileResponse;
import com.extrabite.dto.UserUpdateRequest;

public interface UserService {
    UserProfileResponse getUserProfile(String email);

    UserProfileResponse updateUserProfile(String email, UserUpdateRequest request);
}