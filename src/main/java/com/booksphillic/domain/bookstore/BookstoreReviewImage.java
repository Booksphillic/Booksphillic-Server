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
public class BookstoreReviewImage {

    @Id @GeneratedValue
    @Column(name = "store_review_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "review_id")
    private BookstoreReview storeReview;

    @Column(nullable = false)
    private String url;
}
