package com.quitqecom.dto;

import jakarta.validation.constraints.Min;

public record CartUpdateDto(

        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity

) {
}