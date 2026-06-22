package com.quitqecom.service;

import com.quitqecom.dto.*;
import com.quitqecom.exceptions.ResourceNotFoundException;
import com.quitqecom.mapper.ProductMapper;
import com.quitqecom.model.*;
import com.quitqecom.repository.*;
import com.quitqecom.utility.FileUtility;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    public  final CategoryRepository categoryRepository;
    public final UserRepository userRepository;
    private final ProductMapper productMapper;
    private final SellerRepository sellerRepository;
    private final ProductImageRepository productImageRepository;
    private static final String UPLOAD_LOC = "F:/quitqecom-ui/public/images";

    public List<Product> getAll() {

        return productRepository.findAll();
    }

    public Product addProduct(ProductDto dto) {


        Category category = categoryRepository.findByCategoryName(dto.categoryName())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = productMapper.mapDtoToEntity(dto, category);

        return productRepository.save(product);

    }

    public void addProductV2(
            Principal principal,
            ProductDto dto,
            MultipartFile[] files
    ) throws IOException {

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Seller seller = sellerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        Category category = categoryRepository.findByCategoryName(dto.categoryName())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = productMapper.mapDtoToEntity(dto, category);

        if(files.length == 0){
            throw new RuntimeException(
                    "At least one image required"
            );
        }

        Path uploadPath =
                Paths.get(UPLOAD_LOC);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        /* first image becomes thumbnail */

        String thumbnail =
                files[0].getOriginalFilename();

        product.setImagePath(thumbnail);

        product.setSeller(seller);

        productRepository.save(product);

        /* save all images */

        for(MultipartFile file : files){

            FileUtility.validateFile(file);

            String fileName = file.getOriginalFilename();

            Path destinationPath = uploadPath.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    destinationPath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            ProductImage image =
                    new ProductImage();

            image.setImagePath(fileName);

            image.setProduct(product);

            productImageRepository.save(image);
        }
    }

    public Product getById(int id) {

        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Invalid Product Id"
                        )
                );
    }

    public void deleteById(int id, Principal principal) {

        User user = userRepository
                .findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Seller seller = sellerRepository
                .findByUser(user)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        Product product = getById(id);

        if (product.getSeller().getId() != seller.getId()) {
            throw new RuntimeException(
                    "You can delete only your own products");
        }

        productRepository.delete(product);
    }
    public void update(
            int id,
            Product updatedProduct,
            Principal principal) {

        User user = userRepository
                .findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Seller seller = sellerRepository
                .findByUser(user)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        Product existingProduct = getById(id);

        if (existingProduct.getSeller().getId() != seller.getId()) {
            throw new RuntimeException(
                    "You can update only your own products");
        }

        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStock(updatedProduct.getStock());
        existingProduct.setOfferPercentage(
                updatedProduct.getOfferPercentage()
        );

        productRepository.save(existingProduct);
    }

    public ProductRespDtoV2 getAllWithPagination(int page, int size) {
        Pageable pageable =  PageRequest.of(page,size);
        Page<Product> pages =  productRepository.findAll(pageable);
        return productMapper.mapEntityToDto(pages);
    }

    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<Product> searchProduct(String keyword) {
        return productRepository.findByProductNameContainingIgnoreCase(keyword);
    }


    public ProductCategoryStatRespDto productByCategoryStat() {

        List<ProductCategoryStatJpqlDto> list =
                productRepository.productByCategoryStat();

        List<String> categoryNames = list.stream()
                .map(ProductCategoryStatJpqlDto::categoryName)
                .toList();

        List<Long> productCounts = list.stream()
                .map(ProductCategoryStatJpqlDto::numberOfProducts)
                .toList();

        return new ProductCategoryStatRespDto(
                "Products per Category Stat",
                categoryNames,
                productCounts
        );
    }

    public ProductDetailsRespDto getProductDetails(
            int id
    ) {

        Product product =
                getById(id);

        List<String> images =
                productImageRepository
                        .findByProduct(product)
                        .stream()
                        .map(ProductImage::getImagePath)
                        .toList();

        return new ProductDetailsRespDto(

                product.getId(),
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getOfferPercentage(),
                product.getImagePath(),
                product.getCategory().getCategoryName(),
                images
        );
    }
}