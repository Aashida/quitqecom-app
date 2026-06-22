package com.quitqecom.dto;

public record ReviewResponseDto(
        String customerName,
        int rating,
        String comment
) {
}