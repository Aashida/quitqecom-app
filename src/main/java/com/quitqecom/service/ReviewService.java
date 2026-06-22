package com.quitqecom.service;

import com.quitqecom.dto.ReviewRequestDto;
import com.quitqecom.dto.ReviewResponseDto;
import com.quitqecom.model.*;
import com.quitqecom.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemRepository orderItemRepository;

    private Customer getLoggedInCustomer() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).
                orElseThrow(() -> new RuntimeException("User not found"));
        return customerRepository.findByUser(user).
                orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public String addReview(int productId, ReviewRequestDto dto) {
        Customer customer = getLoggedInCustomer();
        Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException("Product not found"));

        Long purchased = orderItemRepository.hasPurchased(customer.getId(), productId);

        if(purchased == 0) {
            throw new RuntimeException("You can review only purchased products");
        }

        Review review = new Review();

        review.setCustomer(customer);
        review.setProduct(product);
        review.setRating(dto.rating());

        review.setComment(dto.comment());

        reviewRepository.save(review);
        return "Review added successfully";
    }

    public List<ReviewResponseDto>
    getProductReviews(int productId) {

        Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException(
                                        "Product not found"));

        return reviewRepository
                .findByProduct(product)
                .stream()
                .map(review ->
                        new ReviewResponseDto(
                                review.getCustomer()
                                        .getFirstName(),
                                review.getRating(),
                                review.getComment()
                        )
                )
                .toList();
    }

    public Double getAverageRating(
            int productId
    ) {

        return reviewRepository
                .getAverageRating(
                        productId
                );
    }
}