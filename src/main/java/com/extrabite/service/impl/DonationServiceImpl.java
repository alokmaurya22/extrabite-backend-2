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

@Service
public class DonationServiceImpl implements DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDataRepository userDataRepository;

    @Override
    public DonationResponse createDonation(DonationRequest donationRequest, String donorEmail) {
        User donor = userRepository.findByEmail(donorEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + donorEmail));

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
        donation.setCreatedDateTime(LocalDateTime.now());
        donation.setDonor(donor);

        // Increment donation count
        UserData donorData = donor.getUserData();
        donorData.setDonationCount(donorData.getDonationCount() + 1);
        userDataRepository.save(donorData);

        Donation savedDonation = donationRepository.save(donation);
        return convertToResponse(savedDonation);
    }

    @Override
    public DonationResponse getDonationById(Long id) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found with id: " + id));
        return convertToResponse(donation);
    }

    @Override
    public List<DonationResponse> getAllDonations() {
        return donationRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DonationResponse> getPublicDonations() {
        return donationRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DonationResponse> getDonationsByDonor(String donorEmail) {
        User donor = userRepository.findByEmail(donorEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + donorEmail));
        return donationRepository.findAll().stream()
                .filter(donation -> donation.getDonor().equals(donor))
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DonationResponse updateDonation(Long id, DonationRequest donationRequest, String currentUserEmail) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found with id: " + id));

        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + currentUserEmail));

        if (!donation.getDonor().equals(currentUser) && !currentUser.getRole().equals(Role.ADMIN)
                && !currentUser.getRole().equals(Role.SUPER_ADMIN)) {
            throw new RuntimeException("You are not authorized to update this donation");
        }

        donation.setFoodName(donationRequest.getFoodName());
        donation.setDescription(donationRequest.getDescription());
        donation.setQuantity(donationRequest.getQuantity());
        donation.setExpiryDateTime(donationRequest.getExpiryDateTime());
        donation.setFree(donationRequest.isFree());
        donation.setPrice(donationRequest.getPrice());
        donation.setLocation(donationRequest.getLocation());
        donation.setGeolocation(donationRequest.getGeolocation());
        donation.setDeliveryType(donationRequest.getDeliveryType());

        Donation updatedDonation = donationRepository.save(donation);
        return convertToResponse(updatedDonation);
    }

    @Override
    public void deleteDonation(Long id, String currentUserEmail) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found with id: " + id));

        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + currentUserEmail));

        if (!donation.getDonor().equals(currentUser) && !currentUser.getRole().equals(Role.ADMIN)
                && !currentUser.getRole().equals(Role.SUPER_ADMIN)) {
            throw new RuntimeException("You are not authorized to delete this donation");
        }

        // Decrement donation count
        UserData donorData = donation.getDonor().getUserData();
        long currentCount = donorData.getDonationCount();
        if (currentCount > 0) {
            donorData.setDonationCount(currentCount - 1);
            userDataRepository.save(donorData);
        }

        donationRepository.deleteById(id);
    }

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
        return response;
    }
}