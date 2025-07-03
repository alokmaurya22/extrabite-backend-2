package com.extrabite.repository;

import com.extrabite.entity.ModulePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// ModulePermission ke liye repository hai
@Repository
public interface ModulePermissionRepository extends JpaRepository<ModulePermission, Long> {
    Optional<ModulePermission> findByModuleName(String moduleName);
}