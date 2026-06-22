package com.quitqecom.dto;

import java.util.List;

public record ProductDetailsRespDto(

        int id,
        String productName,
        String description,
        double price,
        int stock,
        Integer offerPercentage,
        String imagePath,
        String categoryName,

        List<String> images

) {
}