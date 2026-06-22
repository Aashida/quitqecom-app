package com.quitqecom.dto;

import java.util.List;

public record OrderPageRespDto(

        List<OrderDto> orders,

        int currentPage,

        int totalPages,

        long totalElements,

        int pageSize

) {
}