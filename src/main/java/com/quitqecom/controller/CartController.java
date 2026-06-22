package com.quitqecom.controller;

import com.quitqecom.dto.CartItemRespDto;
import com.quitqecom.dto.CartUpdateDto;
import com.quitqecom.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CartController {

    private final CartService cartService;

    @PostMapping("/add/{productId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public CartItemRespDto addToCart(
            @PathVariable int productId) {

        return cartService.addToCart(productId);
    }

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<CartItemRespDto> getCart() {

        return cartService.getCart();
    }

    @PutMapping("/update/{cartItemId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public CartItemRespDto updateCartItem(
            @PathVariable int cartItemId,
            @RequestBody CartUpdateDto dto) {

        return cartService.updateCartItem(
                cartItemId,
                dto
        );
    }

    @DeleteMapping("/remove/{cartItemId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String removeCartItem(
            @PathVariable int cartItemId) {

        return cartService.removeCartItem(cartItemId);
    }

    @DeleteMapping("/clear")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String clearCart() {

        return cartService.clearCart();
    }

}