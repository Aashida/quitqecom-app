package com.quitqecom.dto;

import java.util.List;

public record CustomerPageRespDto(

        List<CustomerDto> customers,

        int currentPage,

        int totalPages,

        long totalElements,

        int pageSize

) {
}