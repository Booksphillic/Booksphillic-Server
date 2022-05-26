package com.booksphillic.repository.bookstore;

import com.booksphillic.domain.bookstore.BookstoreReview;
import com.booksphillic.domain.bookstore.BookstoreReviewImage;
import com.booksphillic.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BookstoreReviewRepository {

    private final EntityManager em;
    public List<BookstoreReview> findByUser(User user) {
        return em.createQuery("select r from BookstoreReview r" +
                " join fetch r.store s" +
                " where r.user.id=:userId")
                .setParameter("userId", user.getId())
                .getResultList();
    }
}
