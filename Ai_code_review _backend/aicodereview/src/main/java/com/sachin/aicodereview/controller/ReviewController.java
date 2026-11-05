package com.sachin.aicodereview.controller;


import com.sachin.aicodereview.dto.ReviewRequest;
import com.sachin.aicodereview.dto.ReviewResponse;
import com.sachin.aicodereview.entity.CodeReview;
import com.sachin.aicodereview.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/reviews")

public class ReviewController {

    private final ReviewService reviewService;


    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @PostMapping
    public ResponseEntity<ReviewResponse> reviewCode(@RequestBody @Valid ReviewRequest request)
    {
        return  ResponseEntity.ok(
                reviewService.reviewCode(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<CodeReview>> getAllReviews()
    {
        return ResponseEntity.ok(
                reviewService.getAllReviews()
        );
    }

}
