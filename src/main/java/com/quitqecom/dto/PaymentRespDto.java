package com.quitqecom.dto;

import com.quitqecom.enums.PaymentMethod;
import com.quitqecom.enums.PaymentStatus;

public record PaymentRespDto(

        String transactionId,
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus,
        double amount

) {
}
