package com.booksphillic.domain.bookstore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class BookstoreReview {

    @Id
    @GeneratedValue
    @Column(name = "store_review_id")
    private Long id;

    private Long userId;

    private Long storeId;

    @Lob
    @Column(nullable = false)
    private String content;

}
