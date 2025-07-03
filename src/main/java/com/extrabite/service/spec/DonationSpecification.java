package com.extrabite.service.spec;

import com.extrabite.entity.Donation;
import com.extrabite.entity.DonationStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

// Donation ke liye specification class hai
@Component
public class DonationSpecification {

    public Specification<Donation> hasStatus(DonationStatus status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    public Specification<Donation> foodNameContains(String foodName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("foodName")),
                "%" + foodName.toLowerCase() + "%");
    }

    public Specification<Donation> locationContains(String location) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("location")),
                "%" + location.toLowerCase() + "%");
    }

    public Specification<Donation> isFree(Boolean isFree) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("free"), isFree);
    }

    public Specification<Donation> expiresAfter(LocalDateTime dateTime) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("expiryDateTime"),
                dateTime);
    }
}