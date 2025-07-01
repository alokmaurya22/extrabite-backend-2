package com.extrabite.service.impl;

import com.extrabite.dto.FoodRequestCreateDto;
import com.extrabite.dto.FoodRequestResponseDto;
import com.extrabite.dto.FoodRequestSelectPaymentDto;
import com.extrabite.dto.FoodRequestOtpVerifyDto;
import com.extrabite.entity.FoodRequest;
import com.extrabite.entity.FoodRequestStatus;
import com.extrabite.entity.User;
import com.extrabite.entity.PaymentMethod;
import com.extrabite.repository.FoodRequestRepository;
import com.extrabite.repository.UserRepository;
import com.extrabite.service.FoodRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class FoodRequestServiceImpl implements FoodRequestService {
    private final FoodRequestRepository foodRequestRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public FoodRequestResponseDto createFoodRequest(FoodRequestCreateDto dto, String requesterEmail) {
        User requester = userRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + requesterEmail));
        FoodRequest request = new FoodRequest();
        request.setRequester(requester);
        request.setFoodType(dto.getFoodType());
        request.setAlternativeFood(dto.getAlternativeFood());
        request.setNumberOfPeople(dto.getNumberOfPeople());
        request.setMessage(dto.getMessage());
        request.setOfferPrice(dto.getOfferPrice());
        request.setStatus(FoodRequestStatus.OPEN);
        request.setRequestExpiryTime(dto.getRequestExpiryTime());
        request.setRequestedTime(dto.getRequestedTime());
        request.setLocation(dto.getLocation());
        request.setGeolocation(dto.getGeolocation());
        request.setFoodDescription(dto.getFoodDescription());
        request.setContactNumber(dto.getContactNumber());
        request.setPaymentMethod(dto.getPaymentMethod());
        FoodRequest saved = foodRequestRepository.save(request);
        return toDto(saved, requesterEmail);
    }

    @Override
    public List<FoodRequestResponseDto> getAllOpenRequests() {
        return foodRequestRepository.findByStatus(FoodRequestStatus.OPEN)
                .stream().map(r -> toDto(r, null)).collect(Collectors.toList());
    }

    @Override
    public List<FoodRequestResponseDto> getMyRequests(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));
        return foodRequestRepository.findByRequester(user)
                .stream().map(r -> toDto(r, userEmail)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FoodRequestResponseDto offerToFulfill(Long requestId, String fulfillerEmail) {
        FoodRequest request = foodRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found: " + requestId));
        if (request.getStatus() != FoodRequestStatus.OPEN) {
            throw new RuntimeException("Request is not open for offers.");
        }
        if (request.getRequestExpiryTime() != null
                && request.getRequestExpiryTime().isBefore(java.time.LocalDateTime.now())) {
            request.setStatus(FoodRequestStatus.CLOSED);
            foodRequestRepository.save(request);
            throw new RuntimeException("This request has expired and is now closed. No more offers can be made.");
        }
        // Set fulfiller
        User fulfiller = userRepository.findByEmail(fulfillerEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + fulfillerEmail));
        request.setFulfiller(fulfiller);
        // For simplicity, just mark as OFFERED and store fulfiller in message
        request.setStatus(FoodRequestStatus.OFFERED);
        request.setMessage((request.getMessage() == null ? "" : request.getMessage() + "\n") +
                "Offered by: " + fulfillerEmail);
        FoodRequest saved = foodRequestRepository.save(request);
        return toDto(saved, fulfillerEmail);
    }

    @Override
    @Transactional
    public FoodRequestResponseDto acceptOffer(Long requestId, String requesterEmail) {
        FoodRequest request = foodRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found: " + requestId));
        if (request.getStatus() != FoodRequestStatus.OFFERED) {
            throw new RuntimeException("No offer to accept.");
        }
        if (!request.getRequester().getEmail().equals(requesterEmail)) {
            throw new RuntimeException("Only the requester can accept an offer.");
        }
        request.setStatus(FoodRequestStatus.AWAITING_PICKUP);
        request.setPickupCode(generateOtp());
        FoodRequest saved = foodRequestRepository.save(request);
        return toDto(saved, requesterEmail);
    }

    @Override
    @Transactional
    public FoodRequestResponseDto completeRequest(Long requestId, String userEmail) {
        FoodRequest request = foodRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found: " + requestId));
        if (request.getStatus() != FoodRequestStatus.ACCEPTED) {
            throw new RuntimeException("Request is not accepted yet.");
        }
        request.setStatus(FoodRequestStatus.COMPLETED);
        FoodRequest saved = foodRequestRepository.save(request);
        return toDto(saved, userEmail);
    }

    @Override
    @Transactional
    public FoodRequestResponseDto cancelRequest(Long requestId, String userEmail) {
        FoodRequest request = foodRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found: " + requestId));
        if (!request.getRequester().getEmail().equals(userEmail)) {
            throw new RuntimeException("Only the requester can cancel the request.");
        }
        request.setStatus(FoodRequestStatus.CANCELLED);
        FoodRequest saved = foodRequestRepository.save(request);
        return toDto(saved, userEmail);
    }

    @Override
    @Transactional
    public FoodRequestResponseDto verifyOtpAndComplete(Long requestId, String fulfillerEmail,
            FoodRequestOtpVerifyDto dto) {
        FoodRequest request = foodRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found: " + requestId));
        if (request.getStatus() != FoodRequestStatus.AWAITING_PICKUP) {
            throw new RuntimeException("Request is not awaiting pickup.");
        }
        if (request.getPickupCode() == null || !request.getPickupCode().equals(dto.getPickupCode())) {
            throw new RuntimeException("Invalid OTP.");
        }
        // For simplicity, allow any user to complete if they offered
        request.setStatus(FoodRequestStatus.COMPLETED);
        FoodRequest saved = foodRequestRepository.save(request);
        return toDto(saved, fulfillerEmail);
    }

    @Override
    public String getPickupCodeForRequester(Long requestId, String requesterEmail) {
        FoodRequest request = foodRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found: " + requestId));
        if (!request.getRequester().getEmail().equals(requesterEmail)) {
            throw new RuntimeException("You are not authorized to view this pickup code.");
        }
        return request.getPickupCode();
    }

    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(1000000);
        return String.format("%06d", num);
    }

    private FoodRequestResponseDto toDto(FoodRequest request, String currentUserEmail) {
        FoodRequestResponseDto dto = new FoodRequestResponseDto();
        dto.setId(request.getId());
        dto.setRequesterId(request.getRequester().getId());
        dto.setRequesterName(request.getRequester().getFullName());
        dto.setFoodType(request.getFoodType());
        dto.setAlternativeFood(request.getAlternativeFood());
        dto.setNumberOfPeople(request.getNumberOfPeople());
        dto.setMessage(request.getMessage());
        dto.setOfferPrice(request.getOfferPrice());
        dto.setStatus(request.getStatus());
        dto.setCreatedAt(request.getCreatedAt());
        dto.setUpdatedAt(request.getUpdatedAt());
        dto.setRequestExpiryTime(request.getRequestExpiryTime());
        dto.setRequestedTime(request.getRequestedTime());
        dto.setLocation(request.getLocation());
        dto.setGeolocation(request.getGeolocation());
        dto.setFoodDescription(request.getFoodDescription());
        dto.setContactNumber(request.getContactNumber());
        dto.setSubmittedAt(request.getSubmittedAt());
        dto.setPaymentMethod(request.getPaymentMethod());
        if (request.getFulfiller() != null) {
            dto.setFulfillerId(request.getFulfiller().getId());
            dto.setFulfillerName(request.getFulfiller().getFullName());
        }
        // Only show pickupCode to requester (receiver)
        if (currentUserEmail != null &&
                request.getRequester() != null && currentUserEmail.equals(request.getRequester().getEmail())) {
            dto.setPickupCode(request.getPickupCode());
        } else {
            dto.setPickupCode(null);
        }
        return dto;
    }
}