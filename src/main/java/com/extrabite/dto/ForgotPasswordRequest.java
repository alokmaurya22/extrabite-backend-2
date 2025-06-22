package com.extrabite.dto;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
    private String email;
    private String contactNumber;
    private String newPassword;
}
