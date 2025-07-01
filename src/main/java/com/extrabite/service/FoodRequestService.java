package com.extrabite.service;

import com.extrabite.dto.FoodRequestCreateDto;
import com.extrabite.dto.FoodRequestResponseDto;
import com.extrabite.dto.FoodRequestSelectPaymentDto;
import com.extrabite.dto.FoodRequestOtpVerifyDto;
import java.util.List;

public interface FoodRequestService {
    FoodRequestResponseDto createFoodRequest(FoodRequestCreateDto dto, String requesterEmail);

    List<FoodRequestResponseDto> getAllOpenRequests();

    List<FoodRequestResponseDto> getMyRequests(String userEmail);

    FoodRequestResponseDto offerToFulfill(Long requestId, String fulfillerEmail);

    FoodRequestResponseDto acceptOffer(Long requestId, String requesterEmail);

    FoodRequestResponseDto completeRequest(Long requestId, String userEmail);

    FoodRequestResponseDto cancelRequest(Long requestId, String userEmail);

    FoodRequestResponseDto selectPaymentMethod(Long requestId, String userEmail, FoodRequestSelectPaymentDto dto);

    FoodRequestResponseDto verifyOtpAndComplete(Long requestId, String fulfillerEmail, FoodRequestOtpVerifyDto dto);
}