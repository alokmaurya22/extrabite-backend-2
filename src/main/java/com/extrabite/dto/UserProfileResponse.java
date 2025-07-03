package com.extrabite.dto;

import com.extrabite.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

// User ka profile response bhejne ke liye class hai
@Data
@Builder
public class UserProfileResponse {
    private Long id;
    private String fullName;
    private String email;
    private String contactNumber;
    private String location;
    private Role role;
    private LocalDateTime registrationDate;
    private Boolean profileActive;
    private UserDataDto userData;

    @Data
    @Builder
    public static class UserDataDto {
        private String address;
        private String alternateContact;
        private String displayPictureUrl;
        private long donationCount;
        private double rating;
    }
}