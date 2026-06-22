package com.quitqecom.controller;

import com.quitqecom.dto.*;
import com.quitqecom.service.SellerService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.quitqecom.dto.TopProductDto;
import com.quitqecom.dto.OrderStatusSummaryDto;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/seller")
@CrossOrigin(origins = "http://localhost:5173")
public class SellerController {

    private final SellerService sellerService;

    @GetMapping("/profile")
    @PreAuthorize("hasRole('SELLER')")
    public SellerProfileRespDto getProfile() {
        return sellerService.getProfile();
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('SELLER')")
    public SellerProfileRespDto updateProfile(
            @RequestBody SellerUpdateDto dto) {

        return sellerService.updateProfile(dto);
    }

    @GetMapping("/products")
    @PreAuthorize("hasRole('SELLER')")
    public List<SellerProductRespDto> getMyProducts() {

        return sellerService.getMyProducts();
    }

    @GetMapping("/products/{id}")
    @PreAuthorize("hasRole('SELLER')")
    public SellerProductRespDto getMyProduct(
            @PathVariable int id) {

        return sellerService.getMyProduct(id);
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('SELLER')")
    public SellerDashboardDto dashboard() {

        return sellerService.dashboard();
    }

    @GetMapping("/orders")
    @PreAuthorize("hasRole('SELLER')")
    public List<OrderItemDtoV2> orders() {

        return sellerService.orders();
    }

    @GetMapping("/revenue")
    @PreAuthorize("hasRole('SELLER')")
    public RevenueRespDto revenue() {

        return sellerService.revenue();
    }

    @GetMapping("/top-products")
    public List<TopProductDto> topProducts() {

        return sellerService
                .getTopProducts();
    }

    @GetMapping("/status-summary")
    public OrderStatusSummaryDto statusSummary() {

        return sellerService
                .getStatusSummary();
    }

    @GetMapping("/products-sold")
    public Long productsSold() {

        return sellerService
                .getProductsSold();
    }

    @GetMapping("/monthly-revenue")
    public Double monthlyRevenue() {

        return sellerService
                .getMonthlyRevenue();
    }

    @GetMapping("/monthly-revenue-chart")
    public List<MonthlyRevenueChartDto>
    monthlyRevenueChart() {

        return sellerService
                .getMonthlyRevenueChart();
    }

    @GetMapping("/quarterly-revenue")
    public List<QuarterlyRevenueDto>
    quarterlyRevenue() {

        return sellerService
                .getQuarterlyRevenue();
    }

}