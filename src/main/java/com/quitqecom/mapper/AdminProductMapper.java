package com.quitqecom.mapper;

import com.quitqecom.dto.AdminProductDto;
import com.quitqecom.dto.AdminProductPageRespDto;
import com.quitqecom.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminProductMapper {

    public AdminProductPageRespDto
    mapEntityToDto(
            Page<Product> pages
    ) {

        List<AdminProductDto> products =

                pages.getContent()
                        .stream()
                        .map(product ->

                                new AdminProductDto(

                                        product.getId(),

                                        product.getProductName(),

                                        product.getPrice(),

                                        product.getStock(),

                                        product.getCategory()
                                                .getCategoryName(),

                                        product.getSeller()
                                                .getShopName()

                                )

                        )
                        .toList();

        return new AdminProductPageRespDto(

                products,

                pages.getNumber(),

                pages.getTotalPages(),

                pages.getTotalElements(),

                pages.getSize()

        );
    }
}
