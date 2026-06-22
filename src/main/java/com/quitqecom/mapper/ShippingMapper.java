package com.quitqecom.mapper;

import com.quitqecom.dto.ShippingRespDto;
import com.quitqecom.model.Shipping;
import org.springframework.stereotype.Component;

@Component
public class ShippingMapper {

    public ShippingRespDto mapEntityToDto(
            Shipping shipping) {

        return new ShippingRespDto(
                shipping.getReceiverName(),
                shipping.getPhoneNumber(),
                shipping.getAddressLine1(),
                shipping.getAddressLine2(),
                shipping.getCity(),
                shipping.getState(),
                shipping.getPincode(),
                shipping.getCountry(),
                shipping.getShippingStatus(),
                shipping.getTrackingNumber()
        );
    }
}