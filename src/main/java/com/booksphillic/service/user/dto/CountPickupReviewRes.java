package com.booksphillic.service.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class CountPickupReviewRes {

    private int pickupCount;
    private int reviewCount;
    private int phillic;
}
