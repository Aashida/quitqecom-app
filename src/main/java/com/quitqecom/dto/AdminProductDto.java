package com.quitqecom.dto;

public record AdminProductDto(

        int productId,

        String productName,

        double price,

        int stock,

        String categoryName,

        String sellerName

) {
}
