package com.quitqecom.dto;

import com.quitqecom.model.Product;

import java.util.List;
// for Pagination
public record ProductRespDtoV2(
        long totalRecords,
        int totalPages,
        List<Product> data
) {
}
