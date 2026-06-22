package com.quitqecom.mapper;

import com.quitqecom.dto.SellerProductRespDto;
import com.quitqecom.dto.SellerProfileRespDto;
import com.quitqecom.model.Product;
import com.quitqecom.model.Seller;
import org.springframework.stereotype.Component;

@Component
public class SellerMapper {

    public SellerProfileRespDto mapSellerToProfileDto(
            Seller seller) {

        return new SellerProfileRespDto(
                seller.getId(),
                seller.getName(),
                seller.getShopName(),
                seller.getBusinessAddress(),
                seller.getGstNumber(),
                seller.getUser().getEmail(),
                seller.getUser().getUsername()
        );
    }

    public SellerProductRespDto mapProductToDto(
            Product product) {

        return new SellerProductRespDto(
                product.getId(),
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getOfferPercentage(),
                product.getCategory().getCategoryName(),
                product.getImagePath()
        );
    }
}