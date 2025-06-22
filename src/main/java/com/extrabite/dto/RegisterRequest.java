package com.extrabite.dto;

import com.extrabite.entity.Role;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO to capture registration form data.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private String contactNumber;

    private String location;

    @NotNull(message = "Role is required")
    private Role role; // Only Donor or Receiver allowed through registration
}