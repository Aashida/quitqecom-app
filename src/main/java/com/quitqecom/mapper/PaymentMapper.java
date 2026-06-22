package com.quitqecom.mapper;

import com.quitqecom.dto.PaymentRespDto;
import com.quitqecom.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentRespDto mapEntityToDto(
            Payment payment) {

        return new PaymentRespDto(
                payment.getTransactionId(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getAmount()
        );
    }
}
