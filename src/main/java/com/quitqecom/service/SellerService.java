package com.quitqecom.service;

import com.quitqecom.dto.*;
import com.quitqecom.mapper.SellerMapper;
import com.quitqecom.model.OrderItem;
import com.quitqecom.model.Product;
import com.quitqecom.model.Seller;
import com.quitqecom.model.User;
import com.quitqecom.repository.OrderItemRepository;
import com.quitqecom.repository.ProductRepository;
import com.quitqecom.repository.SellerRepository;
import com.quitqecom.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.quitqecom.dto.TopProductDto;
import com.quitqecom.dto.OrderStatusSummaryDto;

import java.time.LocalDate;

import java.util.List;

@Service
@AllArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final SellerMapper sellerMapper;

    public void createSeller(SellerRegisterDto dto, User user) {

        Seller seller = new Seller();

        seller.setName(user.getUsername());
        seller.setShopName(dto.shopName());
        seller.setBusinessAddress(dto.businessAddress());
        seller.setGstNumber(dto.gstNumber());
        seller.setUser(user);

        sellerRepository.save(seller);
    }

    public Seller getLoggedInSeller() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return sellerRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Seller not found"));
    }

    public SellerProfileRespDto getProfile() {

        Seller seller = getLoggedInSeller();

        return sellerMapper.mapSellerToProfileDto(seller);
    }

    public SellerProfileRespDto updateProfile(
            SellerUpdateDto dto) {

        Seller seller = getLoggedInSeller();

        seller.setShopName(dto.shopName());
        seller.setBusinessAddress(dto.businessAddress());
        seller.setGstNumber(dto.gstNumber());

        sellerRepository.save(seller);

        return sellerMapper.mapSellerToProfileDto(seller);
    }

    public List<SellerProductRespDto> getMyProducts() {

        Seller seller = getLoggedInSeller();

        List<Product> products =
                productRepository.findBySeller(seller);

        return products.stream()
                .map(sellerMapper::mapProductToDto)
                .toList();
    }

    public SellerProductRespDto getMyProduct(
            int productId) {

        Seller seller = getLoggedInSeller();

        Product product = productRepository
                .findByIdAndSeller(productId, seller)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found"));

        return sellerMapper.mapProductToDto(product);
    }

    public RevenueRespDto revenue() {

        Seller seller = getLoggedInSeller();

        Double revenue =
                orderItemRepository
                        .calculateRevenue(seller.getId());

        return new RevenueRespDto(revenue);
    }

    public List<OrderItemDtoV2> orders() {

        Seller seller = getLoggedInSeller();

        List<OrderItem> orderItems =
                orderItemRepository
                        .getSellerOrderItems(seller.getId());

        return orderItems.stream()
                .map(oi -> new OrderItemDtoV2(
                        oi.getProduct().getProductName(),
                        oi.getProduct().getPrice(),
                        oi.getQuantity(),
                        oi.getPriceAtPurchase(),
                        oi.getOrder().getId(),
                        oi.getOrder().getStatus()
                ))
                .toList();
    }

    public SellerDashboardDto dashboard() {

        Seller seller = getLoggedInSeller();

        long totalProducts =
                productRepository
                        .findBySeller(seller)
                        .size();

        long totalOrders =
                orderItemRepository
                        .countSellerOrders(seller.getId());

        double revenue =
                orderItemRepository
                        .calculateRevenue(seller.getId());

        return new SellerDashboardDto(
                totalProducts,
                totalOrders,
                revenue
        );
    }


    public List<TopProductDto> getTopProducts() {

        Seller seller = getLoggedInSeller();

        return orderItemRepository
                .topProducts(seller.getId());
    }

    public OrderStatusSummaryDto getStatusSummary() {

        Seller seller = getLoggedInSeller();

        Long placed =
                orderItemRepository
                        .countPlacedOrders(
                                seller.getId());

        Long cancelled =
                orderItemRepository
                        .countCancelledOrders(
                                seller.getId());

        return new OrderStatusSummaryDto(
                placed,
                cancelled
        );
    }

    public Long getProductsSold() {

        Seller seller = getLoggedInSeller();

        return orderItemRepository
                .totalProductsSold(
                        seller.getId());
    }

    public Double getMonthlyRevenue() {

        Seller seller = getLoggedInSeller();

        LocalDate today =
                LocalDate.now();

        return orderItemRepository
                .monthlyRevenue(
                        seller.getId(),
                        today.getYear(),
                        today.getMonthValue()
                );
    }

    public List<MonthlyRevenueChartDto>
    getMonthlyRevenueChart() {

        Seller seller =
                getLoggedInSeller();

        List<Object[]> results =
                orderItemRepository
                        .monthlyRevenueChart(
                                seller.getId()
                        );

        return results.stream()
                .map(r ->
                        new MonthlyRevenueChartDto(
                                ((Number) r[0]).intValue(),
                                ((Number) r[1]).doubleValue()
                        )
                )
                .toList();
    }

    public List<QuarterlyRevenueDto>
    getQuarterlyRevenue() {

        Seller seller =
                getLoggedInSeller();

        List<Object[]> results =
                orderItemRepository
                        .quarterlyRevenue(
                                seller.getId()
                        );

        return results.stream()
                .map(r ->
                        new QuarterlyRevenueDto(
                                "Q" +
                                        ((Number) r[0]).intValue(),
                                ((Number) r[1]).doubleValue()
                        )
                )
                .toList();
    }
}