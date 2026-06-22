package com.quitqecom.repository;

import com.quitqecom.model.Order;
import com.quitqecom.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository
        extends JpaRepository<Payment,Integer> {

    Optional<Payment> findByOrder(Order order);
}