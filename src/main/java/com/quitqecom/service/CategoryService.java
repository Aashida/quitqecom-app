package com.quitqecom.service;

import com.quitqecom.dto.CategoryReqDto;
import com.quitqecom.dto.CategoryRespDto;
import com.quitqecom.mapper.CategoryMapper;
import com.quitqecom.model.Category;
import com.quitqecom.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryRespDto add(CategoryReqDto dto) {

        Category category =
                CategoryMapper.convertToEntity(dto);

        categoryRepository.save(category);

        return CategoryMapper.convertToDto(category);
    }

    public List<CategoryRespDto> getAll() {

        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::convertToDto)
                .toList();
    }

    public CategoryRespDto getById(int id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Category Not Found"));

        return CategoryMapper.convertToDto(category);
    }

    public CategoryRespDto update(int id,
                                  CategoryReqDto dto) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Category Not Found"));

        category.setCategoryName(dto.categoryName());

        categoryRepository.save(category);

        return CategoryMapper.convertToDto(category);
    }

    public String delete(int id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Category Not Found"));

        categoryRepository.delete(category);

        return "Category Deleted Successfully";
    }
}