package com.quitqecom.dto;

public record SellerProfileRespDto(
        int id,
        String name,
        String shopName,
        String businessAddress,
        String gstNumber,
        String email,
        String username
) {
}