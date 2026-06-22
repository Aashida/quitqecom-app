package com.quitqecom.dto;

public record AdminDashboardDto(

        long totalUsers,
        long totalCustomers,
        long totalSellers,
        long totalProducts,
        long totalOrders

) {
}
