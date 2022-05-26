package com.booksphillic.repository.bookstore;

import com.booksphillic.domain.bookstore.BookstoreReview;
import com.booksphillic.domain.bookstore.BookstoreReviewImage;
import com.booksphillic.service.user.dto.GetReviewRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ReviewImageRepository {

    private final EntityManager em;

    public List<BookstoreReviewImage> getReviewImage(BookstoreReview review) {
        return em.createQuery("select ri from BookstoreReviewImage ri" +
                " where ri.storeReview=:review")
                .setParameter("review", review)
                .getResultList();
    }
}
