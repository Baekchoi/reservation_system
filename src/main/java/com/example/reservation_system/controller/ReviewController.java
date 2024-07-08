package com.example.reservation_system.controller;

import com.example.reservation_system.entity.Review;
import com.example.reservation_system.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // 리뷰 작성
    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewService.createReview(review);
    }

    // 리뷰 수정
    @PutMapping("/{id}")
    public Review updateReview(@PathVariable Long id, @RequestBody Review reviewDetails) {
        return reviewService.updateReview(id, reviewDetails);
    }

    // 리뷰 삭제
    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }

    // 모든 리뷰 조회
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    // id로 리뷰 조회
    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }
}
