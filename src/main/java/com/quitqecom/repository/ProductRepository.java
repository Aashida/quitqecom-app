package com.quitqecom.repository;

import com.quitqecom.dto.ProductCategoryStatJpqlDto;
import com.quitqecom.model.Product;
import com.quitqecom.model.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    List<Product> findByProductNameContainingIgnoreCase(String keyword);

    @Query("""
        select p.category.categoryName as categoryName,
        count(p.id) as numberOfProducts
        from Product p
        group by p.category.id
       """)
    List<ProductCategoryStatJpqlDto> productByCategoryStat();

    List<Product> findBySeller(Seller seller);

    Optional<Product> findByIdAndSeller(
            int id,
            Seller seller
    );

    @Query("""
SELECT p
FROM Product p
WHERE
(:search IS NULL OR
LOWER(p.productName)
LIKE LOWER(CONCAT('%', :search, '%')))
AND
(:category IS NULL OR
LOWER(p.category.categoryName)
LIKE LOWER(CONCAT('%', :category, '%')))
AND
(:seller IS NULL OR
LOWER(p.seller.shopName)
LIKE LOWER(CONCAT('%', :seller, '%')))
AND
(
:inStock IS NULL
OR
(:inStock = true AND p.stock > 0)
OR
(:inStock = false AND p.stock = 0)
)
""")
    Page<Product> searchProducts(

            String search,

            String category,

            String seller,

            Boolean inStock,

            Pageable pageable
    );
}
