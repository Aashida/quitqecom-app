package com.quitqecom.mapper;

import com.quitqecom.dto.OrderItemReqDto;
import com.quitqecom.dto.OrderItemRespDto;
import com.quitqecom.model.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    public OrderItem dtoToEntity(OrderItemReqDto dto){

        OrderItem orderItem = new OrderItem();

        orderItem.setQuantity(dto.quantity());

        return orderItem;
    }

    public OrderItemRespDto mapEntityToDto(
            OrderItem orderItem){

        return new OrderItemRespDto(
                orderItem.getProduct().getId(),
                orderItem.getProduct().getProductName(),
                orderItem.getProduct().getPrice(),
                orderItem.getQuantity(),
                orderItem.getPriceAtPurchase()
        );
    }
}