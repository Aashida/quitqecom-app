package com.quitqecom.dto;

import java.util.List;

public record ProductCategoryStatRespDto(
        String title,
        List<String> label,
        List<Long> data
) {
}
