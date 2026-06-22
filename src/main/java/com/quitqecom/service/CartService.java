package com.quitqecom.service;

import com.quitqecom.dto.CartItemRespDto;
import com.quitqecom.dto.CartUpdateDto;
import com.quitqecom.mapper.CartItemMapper;
import com.quitqecom.model.*;
import com.quitqecom.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartItemMapper cartItemMapper;

    private Cart getLoggedInUserCart() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public CartItemRespDto addToCart(int productId) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if(product.getStock() <= 0) {
            throw new RuntimeException("Product out of stock");
        }

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
                .orElse(null);

        if(cartItem != null) {

            cartItem.setQuantity(
                    cartItem.getQuantity() + 1
            );

        } else {

            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
        }

        cartItemRepository.save(cartItem);

        return cartItemMapper
                .mapEntityToDto(cartItem);
    }

    public List<CartItemRespDto> getCart() {

        Cart cart = getLoggedInUserCart();

        List<CartItem> cartItems =
                cartItemRepository.findByCart(cart);

        return cartItems.stream()
                .map(cartItemMapper::mapEntityToDto)
                .toList();
    }

    public CartItemRespDto updateCartItem(int cartItemId, CartUpdateDto dto) {

        Cart cart = getLoggedInUserCart();

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                        .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (cartItem.getCart().getId() != cart.getId()) {
            throw new RuntimeException("Access denied");
        }

        if(dto.quantity() > cartItem.getProduct().getStock()) {
            throw new RuntimeException("Insufficient stock");
        }

        cartItem.setQuantity(dto.quantity());

        cartItemRepository.save(cartItem);

        return cartItemMapper.mapEntityToDto(cartItem);
    }

    public String removeCartItem(
            int cartItemId) {

        Cart cart = getLoggedInUserCart();

        CartItem cartItem =
                cartItemRepository.findById(cartItemId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Cart item not found"));

        if (cartItem.getCart().getId() != cart.getId()) {
            throw new RuntimeException(
                    "Access denied");
        }

        cartItemRepository.delete(cartItem);

        return "Cart item removed successfully";
    }

    public String clearCart() {

        Cart cart = getLoggedInUserCart();

        cartItemRepository.deleteByCart(cart);

        return "Cart cleared successfully";
    }

}