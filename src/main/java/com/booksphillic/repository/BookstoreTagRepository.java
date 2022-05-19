package com.booksphillic.repository;

import com.booksphillic.domain.bookstore.BookstoreTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookstoreTagRepository extends JpaRepository<BookstoreTag, Long> {

    List<BookstoreTag> findByStoreId(Long storeId);
}
