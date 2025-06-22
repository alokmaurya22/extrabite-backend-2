package com.extrabite.dto;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String fullName;
    private String contactNumber;
    private String location;
    private Boolean profileActive;

    // New fields for UserData
    private String address;
    private String alternateContact;
    private String displayPictureUrl;
}