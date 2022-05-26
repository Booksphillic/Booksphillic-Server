package com.booksphillic.repository.tag;

import com.booksphillic.domain.bookstore.BookstoreTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookstoreTagJpaRepository extends JpaRepository<BookstoreTag, Long> {

    public List<BookstoreTag> findByStoreId(Long storeId);
}
