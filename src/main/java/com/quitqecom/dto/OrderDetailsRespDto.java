package com.quitqecom.dto;

import com.quitqecom.enums.OrderStatus;

import java.time.Instant;
import java.util.List;

public record OrderDetailsRespDto(

        int orderId,

        Instant createdAt,

        OrderStatus status,

        double totalAmount,

        List<OrderItemDtoV2> items

) {
}