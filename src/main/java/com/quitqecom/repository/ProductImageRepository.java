package com.quitqecom.repository;

import com.quitqecom.model.Product;
import com.quitqecom.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository
        extends JpaRepository<ProductImage,Integer> {

    List<ProductImage>
    findByProduct(Product product);
}