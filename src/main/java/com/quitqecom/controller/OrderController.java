package com.quitqecom.controller;

import com.quitqecom.dto.OrderDetailsRespDto;
import com.quitqecom.dto.OrderItemDtoV2;
import com.quitqecom.dto.OrderPlaceRespDto;
import com.quitqecom.dto.OrderRespDto;
import com.quitqecom.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    @PreAuthorize("hasRole('CUSTOMER')")
    public OrderPlaceRespDto placeOrder() {

        System.out.println("INSIDE PLACE ORDER");

        return orderService.placeOrder();
    }

    @GetMapping("/my-orders")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<OrderRespDto> myOrders() {

        return orderService.myOrders();
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public OrderDetailsRespDto getOrderDetails(
            @PathVariable int orderId) {

        return orderService.getOrderDetails(orderId);
    }
    @PutMapping("/cancel/{orderId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String cancelOrder(
            @PathVariable int orderId) {

        return orderService.cancelOrder(orderId);
    }
}