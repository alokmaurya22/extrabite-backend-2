package com.extrabite.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// User ke permissions ke liye class hai
@Entity
@Table(name = "module_permissions")
@Getter
@Setter
@NoArgsConstructor
public class ModulePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String moduleName;

    public ModulePermission(String moduleName) {
        this.moduleName = moduleName;
    }
}