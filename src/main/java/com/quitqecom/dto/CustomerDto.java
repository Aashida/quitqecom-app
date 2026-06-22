package com.quitqecom.dto;

public record CustomerDto(

        int customerId,

        String fullName,

        String email,

        String username

) {
}
