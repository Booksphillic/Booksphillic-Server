package com.booksphillic.domain.pickup;

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
public class PickupReviewImage {

    @Id @GeneratedValue
    @Column(name = "pickup_reivew_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pickup_review_id")
    private PickupReview pickupReview;

    @Column(nullable = false)
    private String url;
}
