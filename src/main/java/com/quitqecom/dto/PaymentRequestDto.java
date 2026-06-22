package com.quitqecom.dto;

import com.quitqecom.enums.PaymentMethod;

public record PaymentRequestDto(

        PaymentMethod paymentMethod

) {
}