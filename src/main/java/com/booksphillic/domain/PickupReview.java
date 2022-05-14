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
public class PickupReview {

    @Id @GeneratedValue
    @Column(name = "pickup_review_id")
    private Long id;

    private Long userId;

    private Long pickupId;

    @Lob
    @Column(nullable = false)
    private String content;
}
