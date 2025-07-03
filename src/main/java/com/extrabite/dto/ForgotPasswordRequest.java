package com.extrabite.dto;

import lombok.Data;

// Forgot password ke liye class hai
@Data
public class ForgotPasswordRequest {
    private String email;
    private String contactNumber;
    private String newPassword;
}
