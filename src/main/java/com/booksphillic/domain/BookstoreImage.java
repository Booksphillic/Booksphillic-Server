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
public class BookstoreImage {

    @Id @GeneratedValue
    @Column(name = "store_image_id")
    private Long id;

    private Long bookstoreId;

    private String url;
}
