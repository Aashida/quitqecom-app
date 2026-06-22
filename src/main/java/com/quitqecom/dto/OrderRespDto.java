package com.quitqecom.dto;

import com.quitqecom.enums.OrderStatus;

public record OrderRespDto(
        int orderId,
        String productName,
        OrderStatus status
) {
}