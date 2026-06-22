package com.quitqecom.repository;

import com.quitqecom.model.Order;
import com.quitqecom.model.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShippingRepository extends JpaRepository<Shipping,Integer> {

    Optional<Shipping> findByOrder(Order order);
}