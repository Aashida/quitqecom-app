package com.quitqecom.controller;

import com.quitqecom.dto.ShippingRequestDto;
import com.quitqecom.dto.ShippingRespDto;
import com.quitqecom.dto.ShippingStatusUpdateDto;
import com.quitqecom.service.ShippingService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipping")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ShippingController {

    private final ShippingService shippingService;

    @PostMapping("/create/{orderId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ShippingRespDto createShipping(
            @PathVariable int orderId,
            @RequestBody ShippingRequestDto dto) {

        return shippingService.createShipping(
                orderId,
                dto
        );
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("isAuthenticated()")
    public ShippingRespDto getShipping(
            @PathVariable int orderId) {

        return shippingService.getShipping(orderId);
    }

    @PutMapping("/status/{orderId}")
    @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
    public ShippingRespDto updateStatus(
            @PathVariable int orderId,
            @RequestBody ShippingStatusUpdateDto dto) {

        return shippingService.updateStatus(
                orderId,
                dto
        );
    }
}