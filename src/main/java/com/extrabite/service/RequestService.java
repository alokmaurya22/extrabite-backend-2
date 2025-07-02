package com.extrabite.service;

import com.extrabite.dto.ConfirmPickupRequestDto;
import com.extrabite.dto.RequestResponseDto;
import com.extrabite.dto.PickupCodeResponseDto;
import com.extrabite.entity.PaymentMethod;

import java.util.List;

public interface RequestService {

    // Receiver action: Create a new request for a donation
    RequestResponseDto createRequest(Long donationId, String receiverEmail, PaymentMethod paymentMethod);

    // Donor action: Accept a pending request
    RequestResponseDto acceptRequest(Long requestId, String donorEmail);

    // Donor action: Reject a pending request
    RequestResponseDto rejectRequest(Long requestId, String donorEmail);

    // Donor action: Confirm pickup with OTP
    RequestResponseDto confirmPickup(Long requestId, ConfirmPickupRequestDto pickupRequest, String donorEmail);

    // Receiver action: Get all requests made by the current user
    List<RequestResponseDto> getMySentRequests(String receiverEmail);

    // Donor action: Get all requests received for the current user's donations
    List<RequestResponseDto> getMyReceivedRequests(String donorEmail);

    // Receiver action: Get the pickup code for a request
    PickupCodeResponseDto getPickupCodeForReceiver(Long requestId, String receiverEmail);
}