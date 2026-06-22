package com.quitqecom.repository;

import com.quitqecom.enums.OrderStatus;
import com.quitqecom.model.Customer;
import com.quitqecom.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByCustomer(Customer customer);

    Page<Order> findByStatus(
            OrderStatus status,
            Pageable pageable
    );
}