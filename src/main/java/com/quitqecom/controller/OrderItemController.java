package com.quitqecom.controller;

import com.quitqecom.dto.OrderItemDtoV2;
import com.quitqecom.dto.OrderItemReqDto;
import com.quitqecom.dto.OrderItemRespDto;
import com.quitqecom.model.Order;
import com.quitqecom.service.OrderItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/order/item")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderItemController {

    private final OrderItemService orderItemService;

    /*
     * Req:
     * order should be present in db (orderId)
     * product should be present in db (productId)
     */
    @PostMapping("/add/{orderId}/{productId}")
    public void add(
            @PathVariable int orderId,
            @PathVariable int productId,
            @Valid @RequestBody OrderItemReqDto dto) {

        orderItemService.add(
                orderId,
                productId,
                dto
        );
    }

    /*
     * Get all products belonging to a given order
     */
    @GetMapping("/by-order/{orderId}")
    public List<OrderItemRespDto> getAllProductsByOrder(
            @PathVariable int orderId) {

        return orderItemService
                .getAllProductsByOrder(orderId);
    }

    /*
     * JPQL DTO Projection
     * Returns order + product details
     */
    @GetMapping("/details/{orderId}")
    public List<OrderItemDtoV2> getOrderDetails(
            @PathVariable int orderId) {

        return orderItemService
                .getOrderDetails(orderId);
    }

    /*
     * Get all orders containing a product
     */
    @GetMapping("/by-product/{productId}")
    public List<Order> getOrdersByProduct(
            @PathVariable int productId) {

        return orderItemService
                .getOrdersByProduct(productId);
    }
}