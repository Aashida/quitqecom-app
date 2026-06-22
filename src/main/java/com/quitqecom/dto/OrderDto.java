package com.quitqecom.dto;

import com.quitqecom.enums.OrderStatus;

import java.time.Instant;

public record OrderDto(

        int orderId,

        OrderStatus status,

        String customerName,

        Instant createdAt

) {
}