package com.extrabite.entity;

import jakarta.persistence.*;
import lombok.Data;

// User ka extra data store hota hai yaha
@Entity
@Table(name = "user_data")
@Data
public class UserData {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    private String address;
    private String alternateContact;
    private String displayPictureUrl;
    private long donationCount = 0;
    private double rating = 0.0;

    // Additional fields can be added here
}