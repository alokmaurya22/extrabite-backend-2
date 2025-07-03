package com.extrabite.dto;

import com.extrabite.entity.Role;
import lombok.*;

// Login hone ke baad response bhejne ke liye class hai
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private Long userId;
    private Role role;
    private String message;
}