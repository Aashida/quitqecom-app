package com.quitqecom.dto;

public record ReviewRequestDto(
        int rating,
        String comment
) {
}
