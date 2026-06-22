package com.quitqecom.dto;

import jakarta.validation.constraints.Min;

public record OrderItemReqDto(

        @Min(1)
        int quantity

) {
}
