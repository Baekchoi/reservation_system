package com.example.reservation_system.service;

import com.example.reservation_system.entity.Reservation;
import com.example.reservation_system.entity.Review;
import com.example.reservation_system.repository.ReservationRepository;
import com.example.reservation_system.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    // 리뷰 등록
    public Review createReview(Review review) {
        Reservation reservation = review.getReservation();
        // 예약이 되어있고, 이용 완료 상태이면 리뷰를 등록
        if (reservation != null && "completed".equals(reservation.getStatus())) {
            return reviewRepository.save(review);
        } else {
            throw new RuntimeException("리뷰는 이용 후에 작성할 수 있습니다.");
        }
    }

    // 리뷰 수정
    public Review updateReview(Long id, Review reviewDetails) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
        review.setContent(reviewDetails.getContent());
        review.setRating(reviewDetails.getRating());
        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
        reviewRepository.delete(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
    }
}
