package com.extrabite.dto;

import com.extrabite.entity.Role;
import lombok.*;

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