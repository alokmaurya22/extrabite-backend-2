package com.extrabite.service;

import com.extrabite.dto.DonationRequest;
import com.extrabite.dto.DonationResponse;

import java.util.List;

public interface DonationService {
    DonationResponse createDonation(DonationRequest donationRequest, String donorEmail);

    DonationResponse getDonationById(Long id);

    List<DonationResponse> getAllDonations();

    List<DonationResponse> getDonationsByDonor(String donorEmail);

    DonationResponse updateDonation(Long id, DonationRequest donationRequest, String currentUserEmail);

    void deleteDonation(Long id, String currentUserEmail);

    DonationResponse rejectByPlatform(Long id);

    DonationResponse expireByExpiryTime(Long id);

    List<DonationResponse> changeTargetedDonationStatus(String targetedStatus, String requiredStatus);
}