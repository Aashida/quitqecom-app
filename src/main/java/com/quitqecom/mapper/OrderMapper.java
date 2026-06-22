package com.quitqecom.mapper;

import com.quitqecom.dto.OrderDto;
import com.quitqecom.dto.OrderPageRespDto;
import com.quitqecom.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderPageRespDto
    mapEntityToDto(Page<Order> pages) {

        List<OrderDto> orders =

                pages.getContent()
                        .stream()
                        .map(order ->

                                new OrderDto(

                                        order.getId(),

                                        order.getStatus(),

                                        order.getCustomer()
                                                .getFirstName()
                                                + " "
                                                +
                                                order.getCustomer()
                                                        .getLastName(),

                                        order.getCreatedAt()

                                )

                        )
                        .toList();

        return new OrderPageRespDto(

                orders,

                pages.getNumber(),

                pages.getTotalPages(),

                pages.getTotalElements(),

                pages.getSize()

        );
    }
}