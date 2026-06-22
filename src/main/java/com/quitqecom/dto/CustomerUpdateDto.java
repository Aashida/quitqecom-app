package com.quitqecom.dto;

public record CustomerUpdateDto(

        String firstName,
        String lastName,
        String phoneNumber,
        String address

) {
}