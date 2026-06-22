package com.quitqecom.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminRegisterDto(
        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 4)
        String username,

        @NotBlank
        @Size(min = 6)
        String password
) {
}
