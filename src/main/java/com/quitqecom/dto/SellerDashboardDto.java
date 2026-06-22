package com.quitqecom.dto;

public record SellerDashboardDto(
        long totalProducts,
        long totalOrders,
        double totalRevenue
) {
}