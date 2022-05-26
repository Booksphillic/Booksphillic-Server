package com.booksphillic.repository.pickup;

import com.booksphillic.domain.pickup.Pickup;
import com.booksphillic.domain.pickup.PickupReview;
import com.booksphillic.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PickupReviewJpaRepository extends JpaRepository<PickupReview, Long> {

    Optional<PickupReview> findById(Long reviewId);

    List<PickupReview> findByPickup(Pickup pickup);
}
