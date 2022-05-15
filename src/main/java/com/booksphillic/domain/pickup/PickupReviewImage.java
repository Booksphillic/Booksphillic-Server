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

    private Long pickupReviewId;

    @Column(nullable = false)
    private String url;
}
