package com.quitqecom.dto;

public record OrderStatusSummaryDto(
        Long placedOrders,
        Long cancelledOrders
) {
}
