package com.quitqecom.service;

import com.quitqecom.dto.OrderItemDtoV2;
import com.quitqecom.dto.OrderPlaceRespDto;
import com.quitqecom.dto.OrderRespDto;
import com.quitqecom.enums.OrderStatus;
import com.quitqecom.model.*;
import com.quitqecom.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.quitqecom.dto.OrderDetailsRespDto;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public Order getById(int id){

        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order Id Invalid"));
    }

    private Customer getLoggedInCustomer() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return customerRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));
    }

    @Transactional
    public OrderPlaceRespDto placeOrder() {

        Customer customer = getLoggedInCustomer();

        Cart cart = cartRepository
                .findByUser(customer.getUser())
                .orElseThrow(() ->
                        new RuntimeException("Cart not found"));

        List<CartItem> cartItems =
                cartItemRepository.findByCart(cart);

        if(cartItems.isEmpty()) {
            throw new RuntimeException(
                    "Cart is empty");
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.PLACED);

        orderRepository.save(order);

        for(CartItem cartItem : cartItems) {

            Product product = cartItem.getProduct();

            if(product.getStock()
                    < cartItem.getQuantity()) {

                throw new RuntimeException(
                        product.getProductName()
                                + " out of stock");
            }

            product.setStock(
                    product.getStock()
                            - cartItem.getQuantity());

            productRepository.save(product);

            OrderItem orderItem =
                    new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(
                    cartItem.getQuantity());

            orderItem.setPriceAtPurchase(
                    product.getPrice());

            orderItemRepository.save(orderItem);
        }

        cartItemRepository.deleteByCart(cart);

        return new OrderPlaceRespDto(
                "Order placed successfully",
                order.getId()
        );
    }

    public List<OrderRespDto> myOrders() {

        Customer customer =
                getLoggedInCustomer();

        return orderRepository
                .findByCustomer(customer)
                .stream()
                .map(order -> {

                    List<OrderItem> items =
                            orderItemRepository
                                    .getAllOrderItemsByOrder(
                                            order.getId()
                                    );

                    String productName =
                            items.isEmpty()
                                    ? "No Product"
                                    : items.get(0)
                                    .getProduct()
                                    .getProductName();

                    return new OrderRespDto(
                            order.getId(),
                            productName,
                            order.getStatus()
                    );

                })
                .toList();
    }

    public OrderDetailsRespDto getOrderDetails(
            int orderId) {

        Order order =
                getById(orderId);

        List<OrderItemDtoV2> items =
                orderItemRepository
                        .getOrderDetails(orderId);

        Double totalAmount =
                orderItemRepository
                        .calculateOrderAmount(orderId);

        return new OrderDetailsRespDto(

                order.getId(),

                order.getCreatedAt(),

                order.getStatus(),

                totalAmount,

                items
        );
    }

    public String cancelOrder(
            int orderId) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"));

        if(order.getStatus()
                == OrderStatus.CANCELLED) {

            throw new RuntimeException(
                    "Order already cancelled");
        }

        order.setStatus(
                OrderStatus.CANCELLED);

        orderRepository.save(order);

        return "Order cancelled successfully";
    }
}