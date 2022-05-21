package com.booksphillic.repository.user;

import com.booksphillic.domain.user.UserPickupReviewCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPRCountRepository extends JpaRepository<UserPickupReviewCount, Long> {

    Optional<UserPickupReviewCount> findByUserId(Long userId);
}
