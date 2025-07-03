package com.extrabite.service.spec;

import com.extrabite.entity.Role;
import com.extrabite.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

// User ke liye specification class hai
@Component
public class UserSpecification {

    public Specification<User> hasRole(Role role) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("role"), role);
    }

    public Specification<User> locationContains(String location) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("location")),
                "%" + location.toLowerCase() + "%");
    }

    public Specification<User> isProfileActive(Boolean isActive) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("profileActive"), isActive);
    }
}