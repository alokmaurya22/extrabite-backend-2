package com.extrabite.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

// This class is for user data
// Yaha pe user ki saari info store hoti hai
@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    // Unique id for user
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // UserData ka relation, extra info ke liye
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserData userData;

    // User ka pura naam
    @Column(nullable = false)
    private String fullName;

    // User ka email, unique hona chahiye
    @Column(nullable = false, unique = true)
    private String email;

    // Password encrypted form me store hota hai
    @Column(nullable = false)
    private String password;

    // User ka contact number
    private String contactNumber;

    // User ka location, jaise city ya pin code
    private String location;

    // FSSAI license number (agar hai toh)
    private String fssaiLicenseNumber;

    // User ka role (jaise DONOR, RECEIVER, etc.)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // Kab register hua tha user
    private LocalDateTime registrationDate;

    // Account active hai ya nahi
    private Boolean profileActive;

    // User ke permissions ka set
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_permissions", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<ModulePermission> permissions = new HashSet<>();

    // Jab naya user banta hai, toh userData bhi saath me banta hai
    @PrePersist
    private void initUserData() {
        if (userData == null) {
            userData = new UserData();
            userData.setUser(this);
        }
    }
}