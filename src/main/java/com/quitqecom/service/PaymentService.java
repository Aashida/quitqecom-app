package com.quitqecom.service;

import com.quitqecom.dto.PaymentRequestDto;
import com.quitqecom.dto.PaymentRespDto;
import com.quitqecom.enums.PaymentStatus;
import com.quitqecom.model.Order;
import com.quitqecom.model.Payment;
import com.quitqecom.repository.OrderItemRepository;
import com.quitqecom.repository.OrderRepository;
import com.quitqecom.repository.PaymentRepository;
import com.quitqecom.mapper.PaymentMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentMapper paymentMapper;

    public PaymentRespDto pay(
            int orderId,
            PaymentRequestDto dto) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"));

        if(paymentRepository
                .findByOrder(order)
                .isPresent()) {

            throw new RuntimeException(
                    "Payment already exists");
        }

        Double amount =
                orderItemRepository
                        .calculateOrderAmount(orderId);

        Payment payment = new Payment();

        payment.setOrder(order);
        payment.setAmount(amount);

        payment.setPaymentMethod(
                dto.paymentMethod());

        payment.setPaymentStatus(
                PaymentStatus.SUCCESS);

        payment.setTransactionId(
                UUID.randomUUID()
                        .toString());

        paymentRepository.save(payment);

        return paymentMapper
                .mapEntityToDto(payment);
    }

    public PaymentRespDto getPayment(
            int orderId) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"));

        Payment payment =
                paymentRepository
                        .findByOrder(order)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment not found"));

        return paymentMapper
                .mapEntityToDto(payment);
    }

    public String refund(
            int orderId) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"));

        Payment payment =
                paymentRepository
                        .findByOrder(order)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment not found"));

        payment.setPaymentStatus(
                PaymentStatus.REFUNDED);

        paymentRepository.save(payment);

        return "Payment refunded successfully";
    }
}
