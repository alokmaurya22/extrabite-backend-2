package com.extrabite.service.impl;

import com.extrabite.dto.ConfirmPickupRequestDto;
import com.extrabite.dto.PickupCodeResponseDto;
import com.extrabite.dto.RequestResponseDto;
import com.extrabite.entity.*;
import com.extrabite.repository.DonationRepository;
import com.extrabite.repository.DonationRequestRepository;
import com.extrabite.repository.UserRepository;
import com.extrabite.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final DonationRequestRepository requestRepository;
    private final DonationRepository donationRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public RequestResponseDto createRequest(Long donationId, String receiverEmail, PaymentMethod paymentMethod) {
        Donation donation = findDonation(donationId);
        User receiver = findUser(receiverEmail);

        if (donation.getDonor().equals(receiver)) {
            throw new RuntimeException("You cannot request your own donation.");
        }

        if (donation.getStatus() != DonationStatus.AVAILABLE) {
            throw new RuntimeException("This donation is no longer available.");
        }

        DonationRequest newRequest = new DonationRequest();
        newRequest.setDonation(donation);
        newRequest.setReceiver(receiver);
        newRequest.setDonor(donation.getDonor());
        newRequest.setStatus(RequestStatus.PENDING);
        if (donation.isFree()) {
            newRequest.setPaymentMethod(PaymentMethod.NOT_APPLICABLE);
        } else {
            newRequest.setPaymentMethod(paymentMethod);
        }

        DonationRequest savedRequest = requestRepository.save(newRequest);
        return convertToDto(savedRequest, receiver);
    }

    @Override
    @Transactional
    public RequestResponseDto acceptRequest(Long requestId, String donorEmail) {
        DonationRequest request = findRequest(requestId);
        User donor = findUser(donorEmail);

        if (!request.getDonor().equals(donor)) {
            throw new RuntimeException("You are not authorized to accept this request.");
        }
        if (request.getStatus() != RequestStatus.PENDING) {
            throw new RuntimeException("This request is not in PENDING state.");
        }

        request.setStatus(RequestStatus.AWAITING_PICKUP);
        request.setPickupCode(generateOtp());

        DonationRequest updatedRequest = requestRepository.save(request);
        return convertToDto(updatedRequest, donor);
    }

    @Override
    @Transactional
    public RequestResponseDto rejectRequest(Long requestId, String donorEmail) {
        DonationRequest request = findRequest(requestId);
        User donor = findUser(donorEmail);

        if (!request.getDonor().equals(donor)) {
            throw new RuntimeException("You are not authorized to reject this request.");
        }
        if (request.getStatus() != RequestStatus.PENDING) {
            throw new RuntimeException("This request is not in PENDING state.");
        }

        request.setStatus(RequestStatus.REJECTED);
        DonationRequest updatedRequest = requestRepository.save(request);
        return convertToDto(updatedRequest, donor);
    }

    @Override
    @Transactional
    public RequestResponseDto confirmPickup(Long requestId, ConfirmPickupRequestDto pickupRequest, String donorEmail) {
        DonationRequest request = findRequest(requestId);
        User donor = findUser(donorEmail);

        if (!request.getDonor().equals(donor)) {
            throw new RuntimeException("You are not authorized to confirm this pickup.");
        }
        if (request.getStatus() != RequestStatus.AWAITING_PICKUP) {
            throw new RuntimeException("This request is not awaiting pickup.");
        }
        if (!request.getPickupCode().equals(pickupRequest.getPickupCode())) {
            throw new RuntimeException("Invalid pickup code.");
        }

        request.setStatus(RequestStatus.COMPLETED);

        // Mark original donation as claimed
        Donation donation = request.getDonation();
        donation.setStatus(DonationStatus.CLAIMED);
        donationRepository.save(donation);

        DonationRequest updatedRequest = requestRepository.save(request);
        return convertToDto(updatedRequest, donor);
    }

    @Override
    public List<RequestResponseDto> getMySentRequests(String receiverEmail) {
        User receiver = findUser(receiverEmail);
        return requestRepository.findByReceiver(receiver).stream()
                .map(request -> convertToDto(request, receiver))
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestResponseDto> getMyReceivedRequests(String donorEmail) {
        User donor = findUser(donorEmail);
        return requestRepository.findByDonor(donor).stream()
                .map(request -> convertToDto(request, donor))
                .collect(Collectors.toList());
    }

    @Override
    public PickupCodeResponseDto getPickupCodeForReceiver(Long requestId, String receiverEmail) {
        DonationRequest request = findRequest(requestId);
        if (!request.getReceiver().getEmail().equals(receiverEmail)) {
            throw new RuntimeException("You are not authorized to view this pickup code.");
        }
        PickupCodeResponseDto dto = new PickupCodeResponseDto();
        dto.setPickupCode(request.getPickupCode());
        dto.setDonorContactNumber(request.getDonor().getContactNumber());
        return dto;
    }

    private Donation findDonation(Long donationId) {
        return donationRepository.findById(donationId)
                .orElseThrow(() -> new RuntimeException("Donation not found with id: " + donationId));
    }

    private User findUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    private DonationRequest findRequest(Long requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found with id: " + requestId));
    }

    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(1000000);
        return String.format("%06d", num);
    }

    private RequestResponseDto convertToDto(DonationRequest request, User currentUser) {
        RequestResponseDto response = new RequestResponseDto();
        response.setId(request.getId());
        response.setDonationId(request.getDonation().getId());
        response.setFoodName(request.getDonation().getFoodName());
        response.setReceiverId(request.getReceiver().getId());
        response.setReceiverName(request.getReceiver().getFullName());
        response.setDonorId(request.getDonor().getId());
        response.setDonorName(request.getDonor().getFullName());
        response.setStatus(request.getStatus());
        response.setPaymentMethod(request.getPaymentMethod());
        response.setRequestDate(request.getRequestDate());
        response.setLastUpdateDate(request.getLastUpdateDate());
        response.setFoodType(request.getDonation().getFoodType());
        response.setRefrigerationAvailable(request.getDonation().getRefrigerationAvailable());
        response.setTimer(request.getDonation().getTimer());
        response.setCountdownTime(request.getDonation().getCountdownTime());
        response.setFree(request.getDonation().isFree());
        response.setPrice(request.getDonation().getPrice());
        response.setImageUrl(request.getDonation().getImageUrl());
        response.setLocation(request.getDonation().getLocation()); // Set donation location
        response.setGeolocation(request.getDonation().getGeolocation()); // Set donation geolocation

        // Security check: Only include OTP for the receiver of this request
        if (currentUser != null && currentUser.equals(request.getReceiver())) {
            response.setPickupCode(request.getPickupCode());
        } else {
            response.setPickupCode(null);
        }

        return response;
    }
}