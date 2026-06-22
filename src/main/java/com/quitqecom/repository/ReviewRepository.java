package com.quitqecom.repository;

import com.quitqecom.model.Product;
import com.quitqecom.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository
        extends JpaRepository<Review,Integer> {

    List<Review> findByProduct(Product product);

    @Query("""
    select avg(r.rating)
    from Review r
    where r.product.id=?1
    """)
    Double getAverageRating(
            int productId
    );
}