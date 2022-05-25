package com.booksphillic.repository.pickup;

import com.booksphillic.domain.pickup.PickupReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PickupReviewJpaRepository extends JpaRepository<PickupReview, Long> {

    Optional<PickupReview> findById(Long reviewId);

}
