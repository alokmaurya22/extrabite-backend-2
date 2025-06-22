package com.extrabite.dto;

import com.extrabite.entity.Role;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO to send response after successful registration.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse {

    private Long id;

    private String fullName;

    private String email;

    private String contactNumber;

    private String location;

    private Role role;

    private String message;

    private LocalDateTime registrationDate;

    private Boolean profileActive;

    private String accessToken;
}