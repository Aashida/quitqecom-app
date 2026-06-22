package com.quitqecom.dto;

import com.quitqecom.enums.OrderStatus;

public record OrderItemDtoV2(

        String productName,
        double productPrice,
        int quantity,
        double priceAtPurchase,
        int orderId,
        OrderStatus status

) {
}