package com.extrabite.service;

import com.extrabite.dto.AdminCreationRequest;
import com.extrabite.dto.RegisterResponse;

public interface AdminService {
    RegisterResponse createAdmin(AdminCreationRequest request);
}