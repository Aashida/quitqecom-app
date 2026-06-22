package com.quitqecom.service;

import com.quitqecom.dto.OrderItemDtoV2;
import com.quitqecom.dto.OrderItemReqDto;
import com.quitqecom.dto.OrderItemRespDto;
import com.quitqecom.mapper.OrderItemMapper;
import com.quitqecom.model.Order;
import com.quitqecom.model.OrderItem;
import com.quitqecom.model.Product;
import com.quitqecom.repository.OrderItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderItemService {

    private final OrderService orderService;
    private final ProductService productService;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    public void add(int orderId,
                    int productId,
                    OrderItemReqDto dto){

        Order order =
                orderService.getById(orderId);

        Product product =
                productService.getById(productId);

        OrderItem orderItem =
                orderItemMapper.dtoToEntity(dto);

        orderItem.setOrder(order);

        orderItem.setProduct(product);

        orderItem.setPriceAtPurchase(
                product.getPrice()
        );

        orderItemRepository.save(orderItem);
    }

    public List<OrderItemRespDto> getAllProductsByOrder(
            int orderId){

        orderService.getById(orderId);

        List<OrderItem> list =
                orderItemRepository
                        .getAllOrderItemsByOrder(orderId);

        return list
                .stream()
                .map(orderItemMapper::mapEntityToDto)
                .toList();
    }

    public List<OrderItemDtoV2> getOrderDetails(
            int orderId){
        // by using projection

        orderService.getById(orderId);

        return orderItemRepository
                .getOrderDetails(orderId);
    }

    public List<Order> getOrdersByProduct(
            int productId){

        productService.getById(productId);

        return orderItemRepository
                .getOrdersByProduct(productId);
    }
}