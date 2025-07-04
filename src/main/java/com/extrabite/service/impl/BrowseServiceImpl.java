package com.extrabite.service.impl;

import com.extrabite.dto.DonationResponse;
import com.extrabite.entity.Donation;
import com.extrabite.entity.DonationStatus;
import com.extrabite.repository.DonationRepository;
import com.extrabite.service.BrowseService;
import com.extrabite.service.spec.DonationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Browse se related saari service logic yaha hai
@Service
@RequiredArgsConstructor
public class BrowseServiceImpl implements BrowseService {

    private final DonationRepository donationRepository;
    private final DonationSpecification spec;

    @Override
    public List<DonationResponse> getAllAvailableDonations() {
        // By default, show only AVAILABLE donations. Expiry check removed for easier
        // dev experience.
        Specification<Donation> defaultSpec = Specification
                .where(spec.hasStatus(DonationStatus.AVAILABLE));
        return donationRepository.findAll(defaultSpec).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DonationResponse> searchDonations(Map<String, String> filters) {
        // Start with a blank specification
        Specification<Donation> finalSpec = Specification.where(null);

        if (filters.containsKey("status")) {
            try {
                DonationStatus status = DonationStatus.valueOf(filters.get("status").toUpperCase());
                finalSpec = finalSpec.and(spec.hasStatus(status));
            } catch (IllegalArgumentException e) {
                // Ignore invalid status values
            }
        } else {
            // If no status is specified, default to AVAILABLE
            finalSpec = finalSpec.and(spec.hasStatus(DonationStatus.AVAILABLE));
        }

        if (filters.containsKey("foodName")) {
            finalSpec = finalSpec.and(spec.foodNameContains(filters.get("foodName")));
        }
        if (filters.containsKey("location")) {
            finalSpec = finalSpec.and(spec.locationContains(filters.get("location")));
        }
        if (filters.containsKey("isFree")) {
            finalSpec = finalSpec.and(spec.isFree(Boolean.parseBoolean(filters.get("isFree"))));
        }

        return donationRepository.findAll(finalSpec).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // This helper method can be extracted to a shared mapper class later if needed
    private DonationResponse convertToResponse(Donation donation) {
        DonationResponse response = new DonationResponse();
        response.setId(donation.getId());
        response.setFoodName(donation.getFoodName());
        response.setDescription(donation.getDescription());
        response.setQuantity(donation.getQuantity());
        response.setExpiryDateTime(donation.getExpiryDateTime());
        response.setFree(donation.isFree());
        response.setPrice(donation.getPrice());
        response.setLocation(donation.getLocation());
        response.setGeolocation(donation.getGeolocation());
        response.setDeliveryType(donation.getDeliveryType());
        response.setStatus(donation.getStatus());
        response.setCreatedDateTime(donation.getCreatedDateTime());
        response.setDonorId(donation.getDonor().getId());
        response.setDonorName(donation.getDonor().getFullName());
        response.setFoodType(donation.getFoodType());
        response.setRefrigerationAvailable(donation.getRefrigerationAvailable());
        response.setTimer(donation.getTimer());
        response.setCountdownTime(donation.getCountdownTime());
        response.setImageUrl(donation.getImageUrl());
        return response;
    }
}