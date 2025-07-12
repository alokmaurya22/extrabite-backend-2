package com.extrabite.service.impl;

import com.extrabite.dto.DonationRequest;
import com.extrabite.dto.DonationResponse;
import com.extrabite.entity.Donation;
import com.extrabite.entity.DonationStatus;
import com.extrabite.entity.Role;
import com.extrabite.entity.User;
import com.extrabite.entity.UserData;
import com.extrabite.repository.DonationRepository;
import com.extrabite.repository.UserDataRepository;
import com.extrabite.repository.UserRepository;
import com.extrabite.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// This class handles donation logic
// Yaha pe donation create, update, delete, fetch ka kaam hota hai
@Service
public class DonationServiceImpl implements DonationService {

    // Repository for donation data
    @Autowired
    private DonationRepository donationRepository;

    // Repository for user data
    @Autowired
    private UserRepository userRepository;

    // Repository for extra user data
    @Autowired
    private UserDataRepository userDataRepository;

    // Method to create a new donation
    @Override
    public DonationResponse createDonation(DonationRequest donationRequest, String donorEmail) {
        // Donor ko email se dhoond rahe hain
        User donor = userRepository.findByEmail(donorEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + donorEmail));
        // Naya donation object bana rahe hain
        Donation donation = new Donation();
        donation.setFoodName(donationRequest.getFoodName());
        donation.setDescription(donationRequest.getDescription());
        donation.setQuantity(donationRequest.getQuantity());
        donation.setExpiryDateTime(donationRequest.getExpiryDateTime());
        donation.setFree(donationRequest.isFree());
        donation.setPrice(donationRequest.getPrice());
        donation.setLocation(donationRequest.getLocation());
        donation.setGeolocation(donationRequest.getGeolocation());
        donation.setDeliveryType(donationRequest.getDeliveryType());
        donation.setStatus(DonationStatus.AVAILABLE);
        if (donationRequest.getCreatedDateTime() != null) {
            donation.setCreatedDateTime(donationRequest.getCreatedDateTime());
        } else {
            donation.setCreatedDateTime(LocalDateTime.now());
        }
        donation.setDonor(donor);
        donation.setFoodType(donationRequest.getFoodType());
        donation.setRefrigerationAvailable(donationRequest.getRefrigerationAvailable());
        // Food type ke hisaab se timer set kar rahe hain
        if (donationRequest.getFoodType() != null && donationRequest.getFoodType().name().equals("PRECOOKED")) {
            donation.setTimer(true);
            if (Boolean.TRUE.equals(donationRequest.getRefrigerationAvailable())) {
                donation.setCountdownTime(4 * 60 * 60L); // 4 hours in seconds
            } else {
                donation.setCountdownTime(2 * 60 * 60L); // 2 hours in seconds
            }
        } else if (donationRequest.getFoodType() != null && donationRequest.getFoodType().name().equals("RAW")) {
            donation.setTimer(false);
            donation.setCountdownTime(0L);
        }
        // Set imageUrl if provided
        donation.setImageUrl(donationRequest.getImageUrl());
        // Donation count badha rahe hain
        UserData donorData = donor.getUserData();
        donorData.setDonationCount(donorData.getDonationCount() + 1);
        userDataRepository.save(donorData);
        // Donation ko database me save kar rahe hain
        Donation savedDonation = donationRepository.save(donation);
        return convertToResponse(savedDonation);
    }

    // Method to get donation by id
    @Override
    public DonationResponse getDonationById(Long id) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found with id: " + id));
        return convertToResponse(donation);
    }

    // Method to get all donations
    @Override
    public List<DonationResponse> getAllDonations() {
        return donationRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Method to get all donations by a donor
    @Override
    public List<DonationResponse> getDonationsByDonor(String donorEmail) {
        User donor = userRepository.findByEmail(donorEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + donorEmail));
        return donationRepository.findAll().stream()
                .filter(donation -> donation.getDonor().equals(donor))
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Method to update a donation
    @Override
    public DonationResponse updateDonation(Long id, DonationRequest donationRequest, String currentUserEmail) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found with id: " + id));
        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + currentUserEmail));
        // Sirf donor ya admin hi update kar sakta hai
        if (!donation.getDonor().equals(currentUser) && !currentUser.getRole().equals(Role.ADMIN)
                && !currentUser.getRole().equals(Role.SUPER_ADMIN)) {
            throw new RuntimeException("You are not authorized to update this donation");
        }
        // Donation ki details update kar rahe hain
        donation.setFoodName(donationRequest.getFoodName());
        donation.setDescription(donationRequest.getDescription());
        donation.setQuantity(donationRequest.getQuantity());
        donation.setExpiryDateTime(donationRequest.getExpiryDateTime());
        donation.setFree(donationRequest.isFree());
        donation.setPrice(donationRequest.getPrice());
        donation.setLocation(donationRequest.getLocation());
        donation.setGeolocation(donationRequest.getGeolocation());
        donation.setDeliveryType(donationRequest.getDeliveryType());
        donation.setFoodType(donationRequest.getFoodType());
        donation.setRefrigerationAvailable(donationRequest.getRefrigerationAvailable());
        // Food type ke hisaab se timer set kar rahe hain
        if (donationRequest.getFoodType() != null && donationRequest.getFoodType().name().equals("PRECOOKED")) {
            donation.setTimer(true);
            if (Boolean.TRUE.equals(donationRequest.getRefrigerationAvailable())) {
                donation.setCountdownTime(4 * 60 * 60L); // 4 hours in seconds
            } else {
                donation.setCountdownTime(2 * 60 * 60L); // 2 hours in seconds
            }
        } else if (donationRequest.getFoodType() != null && donationRequest.getFoodType().name().equals("RAW")) {
            donation.setTimer(false);
            donation.setCountdownTime(0L);
        }
        // Set imageUrl if provided
        donation.setImageUrl(donationRequest.getImageUrl());
        Donation updatedDonation = donationRepository.save(donation);
        return convertToResponse(updatedDonation);
    }

    // Method to delete a donation
    @Override
    public void deleteDonation(Long id, String currentUserEmail) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found with id: " + id));
        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + currentUserEmail));
        // Sirf donor ya admin hi delete kar sakta hai
        if (!donation.getDonor().equals(currentUser) && !currentUser.getRole().equals(Role.ADMIN)
                && !currentUser.getRole().equals(Role.SUPER_ADMIN)) {
            throw new RuntimeException("You are not authorized to delete this donation");
        }
        // Donation count kam kar rahe hain
        UserData donorData = donation.getDonor().getUserData();
        long currentCount = donorData.getDonationCount();
        if (currentCount > 0) {
            donorData.setDonationCount(currentCount - 1);
            userDataRepository.save(donorData);
        }
        // Donation ko database se hata rahe hain
        donationRepository.deleteById(id);
    }

    // Method to convert Donation entity to response object
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

    // Platform can reject a donation if timer expired
    @Override
    public DonationResponse rejectByPlatform(Long id) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found with id: " + id));
        if (donation.getStatus() == DonationStatus.AVAILABLE
                && Boolean.TRUE.equals(donation.getTimer())
                && donation.getCreatedDateTime().plusSeconds(donation.getCountdownTime())
                        .isBefore(java.time.LocalDateTime.now())) {
            donation.setStatus(DonationStatus.EXPIRED);
            donationRepository.save(donation);
        }
        return convertToResponse(donation);
    }

    @Override
    public DonationResponse expireByExpiryTime(Long id) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found with id: " + id));
        if (donation.getExpiryDateTime() != null
                && donation.getExpiryDateTime().isBefore(java.time.LocalDateTime.now())) {
            donation.setStatus(DonationStatus.EXPIRED_BY_EXP_TIME);
            donationRepository.save(donation);
        }
        return convertToResponse(donation);
    }

    @Override
    public List<DonationResponse> changeTargetedDonationStatus(String targetedStatus, String requiredStatus) {
        List<Donation> donations = donationRepository.findAll();
        List<DonationResponse> changed = new java.util.ArrayList<>();
        for (Donation donation : donations) {
            if (donation.getStatus().name().equalsIgnoreCase(targetedStatus)) {
                try {
                    donation.setStatus(DonationStatus.valueOf(requiredStatus));
                    donationRepository.save(donation);
                    changed.add(convertToResponse(donation));
                } catch (IllegalArgumentException e) {
                    // Invalid status provided, skip
                }
            }
        }
        return changed;
    }
}