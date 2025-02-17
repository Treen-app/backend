package com.app.treen.jpa.repository.review;

import com.app.treen.review.entity.Review;
import com.app.treen.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByTransactionId(Long transactionId); // 특정 거래에 대한 후기 조회
    List<Review> findByReviewedUser(User reviewedUser); // 내가 받은 후기 리스트 조회 (리뷰 대상이 된 사용자 기준)
}
