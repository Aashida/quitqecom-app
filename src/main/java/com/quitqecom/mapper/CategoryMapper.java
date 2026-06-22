package com.quitqecom.mapper;

import com.quitqecom.dto.CategoryReqDto;
import com.quitqecom.dto.CategoryRespDto;
import com.quitqecom.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public static Category convertToEntity(CategoryReqDto dto) {

        Category category = new Category();

        category.setCategoryName(dto.categoryName());

        return category;
    }

    public static CategoryRespDto convertToDto(Category category) {

        return new CategoryRespDto(
                category.getId(),
                category.getCategoryName()
        );
    }
}