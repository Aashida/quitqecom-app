package com.quitqecom.service;

import com.quitqecom.dto.ShippingRequestDto;
import com.quitqecom.dto.ShippingRespDto;
import com.quitqecom.dto.ShippingStatusUpdateDto;
import com.quitqecom.enums.ShippingStatus;
import com.quitqecom.mapper.ShippingMapper;
import com.quitqecom.model.Order;
import com.quitqecom.model.Shipping;
import com.quitqecom.repository.OrderRepository;
import com.quitqecom.repository.ShippingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ShippingService {

    private final ShippingRepository shippingRepository;
    private final OrderRepository orderRepository;
    private final ShippingMapper shippingMapper;

    public ShippingRespDto createShipping(
            int orderId,
            ShippingRequestDto dto) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"));

        if(shippingRepository
                .findByOrder(order)
                .isPresent()) {

            throw new RuntimeException(
                    "Shipping already exists");
        }

        Shipping shipping = new Shipping();

        shipping.setOrder(order);

        shipping.setReceiverName(
                dto.receiverName());

        shipping.setPhoneNumber(
                dto.phoneNumber());

        shipping.setAddressLine1(
                dto.addressLine1());

        shipping.setAddressLine2(
                dto.addressLine2());

        shipping.setCity(
                dto.city());

        shipping.setState(
                dto.state());

        shipping.setPincode(
                dto.pincode());

        shipping.setCountry(
                dto.country());

        shipping.setShippingStatus(
                ShippingStatus.PENDING);

        shipping.setTrackingNumber(
                "TRK-" +
                        UUID.randomUUID()
                                .toString()
                                .substring(0,8));

        shippingRepository.save(shipping);

        return shippingMapper
                .mapEntityToDto(shipping);
    }

    public ShippingRespDto getShipping(
            int orderId) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"));

        Shipping shipping =
                shippingRepository
                        .findByOrder(order)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Shipping not found"));

        return shippingMapper
                .mapEntityToDto(shipping);
    }

    public ShippingRespDto updateStatus(
            int orderId,
            ShippingStatusUpdateDto dto) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"));

        Shipping shipping =
                shippingRepository
                        .findByOrder(order)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Shipping not found"));

        shipping.setShippingStatus(
                dto.status());

        shippingRepository.save(shipping);

        return shippingMapper
                .mapEntityToDto(shipping);
    }
}
