package com.extrabite.dto;

import com.extrabite.entity.PaymentMethod;
import lombok.Data;

@Data
public class FoodRequestSelectPaymentDto {
    private PaymentMethod paymentMethod;
}