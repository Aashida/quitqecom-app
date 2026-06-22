package com.quitqecom.controller;

import com.quitqecom.dto.PaymentRequestDto;
import com.quitqecom.dto.PaymentRespDto;
import com.quitqecom.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/pay/{orderId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public PaymentRespDto pay(
            @PathVariable int orderId,
            @RequestBody PaymentRequestDto dto) {

        return paymentService.pay(orderId, dto);
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public PaymentRespDto getPayment(
            @PathVariable int orderId) {

        return paymentService.getPayment(orderId);
    }

    @PostMapping("/refund/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String refund(
            @PathVariable int orderId) {

        return paymentService.refund(orderId);
    }
}