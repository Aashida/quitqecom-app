package com.quitqecom.service;


import com.quitqecom.dto.ProductDto;
import com.quitqecom.exceptions.ResourceNotFoundException;
import com.quitqecom.model.Category;
import com.quitqecom.model.Product;
import com.quitqecom.model.Seller;
import com.quitqecom.model.User;
import com.quitqecom.repository.CategoryRepository;
import com.quitqecom.repository.ProductRepository;
import com.quitqecom.repository.SellerRepository;
import com.quitqecom.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private Principal principal;


    private Category category;
    private Seller seller;
    private User user;

    private Product product1;
    private Product product2;

    @BeforeEach
    public void SampleData(){
        category = new Category();
        category.setCategoryName("Phones");

        user = new User();

        seller = new Seller();
        seller.setId(1);


        product1 = new Product();
        product1.setId(1);
        product1.setProductName("IPhone 13");
        product1.setDescription("Good Camera");
        product1.setPrice(50);
        product1.setStock(25);
        product1.setCategory(category);
        product1.setSeller(seller);

        product2 = new Product();
        product2.setId(2);
        product2.setProductName("Samsung");
        product2.setDescription("Good Camera");
        product2.setPrice(50);
        product2.setStock(25);
        product2.setCategory(category);

    }

    @Test
    void getById_ProductExists(){
        when(productRepository.findById(100)).thenReturn(Optional.of(product1));
        when(productRepository.findById(200)).thenReturn(Optional.of(product2));

        assertThat(productService.getById(100).getId()).isEqualTo(1);
        assertThat(productService.getById(200).getId()).isEqualTo(2);

        assertThat(productService.getById(100).getProductName()).isEqualTo("IPhone 13");
        assertThat(productService.getById(200).getProductName()).isEqualTo("Samsung");

    }

    @Test
    void getById_productDoesNotExist(){
        when(productRepository.findById(100)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> productService.getById(100))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Invalid Product Id");
        verify(productRepository, times(1)).findById(100);
    }


    @Test
    void addProduct1_mustSaveProduct() {
        when(categoryRepository.findByCategoryName("Phones")).thenReturn(Optional.of(category));

        ProductDto dto = new ProductDto(
                "IPhone 13",
                "Good Camera",
                50,
                25,
                "Phones"
        );

        when(productRepository.save(any(Product.class))).thenReturn(product1);

        Product actualProduct = productService.addProduct(dto);
        assertThat(actualProduct.getProductName()).isEqualTo(product1.getProductName());
        assertThat(actualProduct.getDescription()).isEqualTo(product1.getDescription());
        assertThat(actualProduct.getPrice()).isEqualTo(product1.getPrice());
        assertThat(actualProduct.getStock()).isEqualTo(product1.getStock());
        assertThat(actualProduct.getCategory()).isEqualTo(product1.getCategory());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void deleteProduct_mustDeleteAndReturnNothing() {

        when(principal.getName()).thenReturn("aashi");

        when(userRepository.findByUsername("aashi")).thenReturn(Optional.of(user));
        when(sellerRepository.findByUser(user)).thenReturn(Optional.of(seller));

        when(productRepository.findById(100)).thenReturn(Optional.of(product1));

        doNothing().when(productRepository).delete(product1);
        productService.deleteById(100, principal);

        verify(productRepository, times(1)).delete(product1);
    }


}
