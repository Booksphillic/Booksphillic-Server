package com.booksphillic.repository.bookstore;

import com.booksphillic.domain.bookstore.BookstoreReview;
import com.booksphillic.domain.bookstore.BookstoreReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewImageJpaRepository extends JpaRepository<BookstoreReviewImage, Long> {
    Optional<BookstoreReviewImage> findById(Long id);

    List<BookstoreReviewImage> findByStoreReview(BookstoreReview bookstoreReview);
}
