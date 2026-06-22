package com.quitqecom.dto;

public record ShippingRequestDto(

        String receiverName,
        String phoneNumber,
        String addressLine1,
        String addressLine2,
        String city,
        String state,
        String pincode,
        String country

) {
}