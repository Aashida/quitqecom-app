package com.quitqecom.dto;

public record SellerProductRespDto(
        int id,
        String productName,
        String description,
        double price,
        int stock,
        Integer offerPercentage,
        String categoryName,
        String imagePath
) {
}