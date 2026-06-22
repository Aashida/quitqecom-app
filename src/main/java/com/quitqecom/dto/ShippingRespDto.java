package com.quitqecom.dto;

import com.quitqecom.enums.ShippingStatus;

public record ShippingRespDto(

        String receiverName,
        String phoneNumber,
        String addressLine1,
        String addressLine2,
        String city,
        String state,
        String pincode,
        String country,
        ShippingStatus shippingStatus,
        String trackingNumber

) {
}