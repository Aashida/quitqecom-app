package com.quitqecom.repository;


import com.quitqecom.model.Seller;
import com.quitqecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SellerRepository extends JpaRepository<Seller, Integer> {
    Optional<Seller> findByUserEmail(String email);
    Optional<Seller> findByUser(User user);
}
