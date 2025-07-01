package com.extrabite.dto;

import com.extrabite.entity.DeliveryType;
import com.extrabite.entity.PaymentMethod;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DonationRequest {
    private String foodName;
    private String description;
    private String quantity;
    private LocalDateTime expiryDateTime;
    private boolean free;
    private Double price;
    private String location;
    private String geolocation;
    private DeliveryType deliveryType;
    private PaymentMethod paymentMethod;
}