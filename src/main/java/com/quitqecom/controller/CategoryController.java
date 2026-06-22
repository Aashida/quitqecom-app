package com.quitqecom.controller;

import com.quitqecom.dto.CategoryReqDto;
import com.quitqecom.dto.CategoryRespDto;
import com.quitqecom.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/add")
    public CategoryRespDto add(
            @RequestBody CategoryReqDto dto) {

        return categoryService.add(dto);
    }

    @GetMapping("/all")
    public List<CategoryRespDto> getAll() {

        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public CategoryRespDto getById(
            @PathVariable int id) {

        return categoryService.getById(id);
    }

    @PutMapping("/update/{id}")
    public CategoryRespDto update(
            @PathVariable int id,
            @RequestBody CategoryReqDto dto) {

        return categoryService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable int id) {

        return categoryService.delete(id);
    }
}