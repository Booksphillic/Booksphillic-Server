package com.booksphillic.domain;

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

    private Long storeReviewId;

    @Column(nullable = false)
    private String url;
}
