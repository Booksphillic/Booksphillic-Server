package com.booksphillic.repository.pickup;

import com.booksphillic.domain.pickup.PickupReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PickupReviewImageJpaRepository extends JpaRepository<PickupReviewImage, Long> {

    Optional<PickupReviewImage> findById(Long id);

}
