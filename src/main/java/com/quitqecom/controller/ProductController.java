package com.quitqecom.controller;

import com.quitqecom.dto.ProductCategoryStatRespDto;
import com.quitqecom.dto.ProductDto;
import com.quitqecom.dto.ProductRespDtoV2;
import com.quitqecom.model.Product;
import com.quitqecom.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public List<Product> getAll() {

        return productService.getAll();
    }

    @GetMapping("/all/v2")
    public ProductRespDtoV2 getAllV2(@RequestParam int page,
                                     @RequestParam int size){
        return productService.getAllWithPagination( page,size);
    }

    @PostMapping("/add")
    public Product addProduct(@Valid @RequestBody ProductDto dto) {

        return productService.addProduct(dto);

    }
    //add product
    @PostMapping(value="/addV2",
            consumes = "multipart/form-data")
    public void addProductV2(
            Principal principal,
            @ModelAttribute ProductDto dto,
            @RequestParam("files")
            MultipartFile[] files
    ) throws IOException {

        productService.addProductV2(
                principal,
                dto,
                files
        );
    }

    @GetMapping("/get-one/{id}")
    public ResponseEntity<Object> getById(
            @PathVariable int id
    )
    {
        return ResponseEntity.ok(
                productService.getProductDetails(id)
        );
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(
            @PathVariable int id,
            Principal principal) {

        productService.deleteById(id, principal);
    }

    @PutMapping("/update/{id}")
    public void update(
            @PathVariable int id,
            @RequestBody Product updatedProduct,
            Principal principal
    ) {
        productService.update(id, updatedProduct, principal);
    }

    @GetMapping("/price-range")
    public List<Product> getProductsByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice){
        return productService.getProductsByPriceRange(minPrice, maxPrice);
    }

    @GetMapping("/search")
    public List<Product> searchProduct(
            @RequestParam String keyword) {

        return productService.searchProduct(keyword);
    }

    @GetMapping("/category/stat")
    public ProductCategoryStatRespDto productByCategoryStat() {
        return productService.productByCategoryStat();
    }

}