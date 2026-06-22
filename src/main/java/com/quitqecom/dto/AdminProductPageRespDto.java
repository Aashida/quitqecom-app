package com.quitqecom.dto;

import java.util.List;

public record AdminProductPageRespDto(

        List<AdminProductDto> products,

        int currentPage,

        int totalPages,

        long totalElements,

        int pageSize

) {
}
