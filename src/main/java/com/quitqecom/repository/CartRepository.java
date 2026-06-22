package com.quitqecom.repository;

import com.quitqecom.model.Cart;
import com.quitqecom.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository
        extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByUser(User user);

    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.cart = :cart")
    void deleteByCart(@Param("cart") Cart cart);
}