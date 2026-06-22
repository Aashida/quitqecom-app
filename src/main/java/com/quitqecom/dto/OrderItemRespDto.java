package com.quitqecom.dto;

public record OrderItemRespDto(

        int productId,
        String productName,
        double currentPrice,
        int quantity,
        double priceAtPurchase

) {
}
