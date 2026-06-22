package com.quitqecom.mapper;

import com.quitqecom.dto.ProductDto;
import com.quitqecom.dto.ProductRespDtoV2;
import com.quitqecom.model.Category;
import com.quitqecom.model.Product;
import com.quitqecom.model.Seller;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {
    public static Product mapDtoToEntity(ProductDto dto,
                                  Category category) {

        Product product = new Product();

        product.setProductName(dto.productName());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStock(dto.stock());
        product.setCategory(category);

        return product;
    }

    public ProductRespDtoV2 mapEntityToDto(Page<Product> pages){
        long totalElements = pages.getTotalElements();
        int totalPages = pages.getTotalPages();
        List<Product> list = pages.getContent();
        ProductRespDtoV2 dto = new ProductRespDtoV2(
                totalElements,
                totalPages,
                list
        );
        return dto;
    }
}
