package com.quitqecom.service;

import com.quitqecom.dto.CategoryReqDto;
import com.quitqecom.dto.CategoryRespDto;
import com.quitqecom.model.Category;
import com.quitqecom.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category1;
    private Category category2;

    @BeforeEach
    public void sampleData(){

        category1 = new Category();
        category1.setId(1);
        category1.setCategoryName("Electronics");

        category2 = new Category();
        category2.setId(2);
        category2.setCategoryName("Art");
    }

    @Test
    void getAllCategories_MustReturnSomething(){

        when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));

        List<CategoryRespDto> actualCall = categoryService.getAll();

        assertThat(actualCall).hasSize(2);

        assertThat(actualCall.getFirst().categoryName()).isEqualToIgnoringCase("Electronics");
        assertThat(actualCall.get(1).categoryName()).isEqualToIgnoringCase("Art");
    }

    @Test
    void getById_categoryExists(){

        when(categoryRepository.findById(100)).thenReturn(Optional.of(category1));
        when(categoryRepository.findById(200)).thenReturn(Optional.of(category2));

        assertThat(categoryService.getById(100).id()).isEqualTo(1);
        assertThat(categoryService.getById(200).id()).isEqualTo(2);

        assertThat(categoryService.getById(100).categoryName()).isEqualTo("Electronics");
        assertThat(categoryService.getById(200).categoryName()).isEqualTo("Art");
    }

    @Test
    void getById_categoryDoesNotExist(){

        when(categoryRepository.findById(100)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.getById(100))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Category Not Found");

        verify(categoryRepository, times(1)).findById(100);
    }

    @Test
    void addCategory_mustSaveCategory(){

        CategoryReqDto dto = new CategoryReqDto("Electronics");

        when(categoryRepository.save(any(Category.class))).thenReturn(category1);

        CategoryRespDto actualCategory = categoryService.add(dto);

        assertThat(actualCategory.categoryName()).isEqualTo(category1.getCategoryName());

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void deleteCategory_mustDeleteAndReturnNothing(){

        when(categoryRepository.findById(100)).thenReturn(Optional.of(category1));

        doNothing().when(categoryRepository).delete(category1);

        String message = categoryService.delete(100);

        assertThat(message).isEqualTo("Category Deleted Successfully");

        verify(categoryRepository, times(1)).delete(category1);
    }
}