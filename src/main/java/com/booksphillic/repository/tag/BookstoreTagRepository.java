package com.booksphillic.repository.tag;

import com.booksphillic.domain.bookstore.BookstoreTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookstoreTagRepository{

    private final EntityManager em;

    public List<BookstoreTag> findStoreByName(String name) {
        String jpql = "select t from BookstoreTag t " +
                "join fetch t.store as s " +
                "join t.tag as tg " +
                "where tg.name = :name";
         return em.createQuery(jpql, BookstoreTag.class)
                .setParameter("name", name)
                .getResultList();
    }
}
