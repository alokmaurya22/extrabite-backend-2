package com.extrabite.service;

import com.extrabite.dto.LoginRequest;
import com.extrabite.dto.LoginResponse;
import com.extrabite.dto.RegisterRequest;
import com.extrabite.dto.RegisterResponse;

/**
 * Service interface to define authentication operations.
 */
public interface AuthService {

    RegisterResponse registerNewUser(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}