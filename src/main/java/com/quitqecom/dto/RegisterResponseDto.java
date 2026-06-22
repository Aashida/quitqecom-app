package com.quitqecom.dto;

import com.quitqecom.enums.Role;

public record RegisterResponseDto(
        int id,
        String username,
        String email,
        String role,
        String message
) {
}
