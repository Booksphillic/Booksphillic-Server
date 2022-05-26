package com.booksphillic.repository.bookstore;

import com.booksphillic.domain.bookstore.Bookstore;
import com.booksphillic.domain.bookstore.BookstoreReview;
import com.booksphillic.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookstoreReviewJpaRepository extends JpaRepository<BookstoreReview, Long> {

    Optional<BookstoreReview> findById(Long reviewId);

    List<BookstoreReview> findByStore(Bookstore bookstore);
}
