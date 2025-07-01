package com.extrabite.service;

import com.extrabite.dto.AdminCreationRequest;
import com.extrabite.dto.UserProfileResponse;

public interface SuperAdminService {
    UserProfileResponse registerAdmin(AdminCreationRequest request);

    void blockUser(Long userId);

    void unblockUser(Long userId);

    UserProfileResponse getUserById(Long userId);
}