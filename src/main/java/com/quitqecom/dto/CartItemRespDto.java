package com.quitqecom.dto;

public record CartItemRespDto(

        int cartItemId,
        int productId,
        String productName,
        int quantity,
        double price

) {
}