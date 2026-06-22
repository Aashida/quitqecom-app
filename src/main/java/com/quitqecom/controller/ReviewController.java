package com.quitqecom.controller;

import com.quitqecom.dto.ReviewRequestDto;
import com.quitqecom.dto.ReviewResponseDto;
import com.quitqecom.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/add/{productId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String addReview(
            @PathVariable int productId,
            @RequestBody ReviewRequestDto dto
    ) {

        return reviewService.addReview(
                productId,
                dto
        );
    }

    @GetMapping("/product/{productId}")
    public List<ReviewResponseDto>
    getReviews(
            @PathVariable int productId
    ) {

        return reviewService
                .getProductReviews(
                        productId
                );
    }

    @GetMapping("/rating/{productId}")
    public Double getAverageRating(
            @PathVariable int productId
    ) {

        return reviewService
                .getAverageRating(
                        productId
                );
    }
}