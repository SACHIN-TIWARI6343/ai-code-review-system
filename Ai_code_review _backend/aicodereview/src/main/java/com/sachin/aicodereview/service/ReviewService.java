package com.sachin.aicodereview.service;

import com.sachin.aicodereview.ai.GeminiService;
import com.sachin.aicodereview.dto.ReviewRequest;
import com.sachin.aicodereview.dto.ReviewResponse;
import com.sachin.aicodereview.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import com.sachin.aicodereview.entity.CodeReview;
import java.time.LocalDateTime;
import  java.util.List;

@Service
public class ReviewService {

    private  final ReviewRepository reviewRepository;
    private final GeminiService geminiService;

    public ReviewService(ReviewRepository reviewRepository, GeminiService geminiService) {
        this.reviewRepository = reviewRepository;
        this.geminiService = geminiService;
    }

    public List<CodeReview> getAllReviews()
    {
        return  reviewRepository.findAll();
    }
    public ReviewResponse reviewCode(ReviewRequest request)
    {
        CodeReview review = new CodeReview();

        review.setLanguage(request.getLanguage());
        review.setCode(request.getCode());

        review.setReview("Code reviewed successfully.");

        String aiReview = geminiService.reviewCode(request.getCode());
        review.setReview(aiReview);

        review.setCreatedAt(LocalDateTime.now());

        reviewRepository.save(review);

        return  new ReviewResponse( aiReview );

    }


}
