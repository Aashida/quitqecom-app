package com.quitqecom.mapper;

import com.quitqecom.dto.CartItemRespDto;
import com.quitqecom.model.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    public CartItemRespDto mapEntityToDto(
            CartItem cartItem) {

        return new CartItemRespDto(
                cartItem.getId(),
                cartItem.getProduct().getId(),
                cartItem.getProduct().getProductName(),
                cartItem.getQuantity(),
                cartItem.getProduct().getPrice()
        );
    }
}
