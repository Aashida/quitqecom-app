package com.quitqecom.dto;

public record CustomerProfileRespDto(

        int id,
        String firstName,
        String lastName,
        String phoneNumber,
        String address,
        String email,
        String username

) {
}
