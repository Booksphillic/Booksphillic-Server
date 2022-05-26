package com.booksphillic.service.pickup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PickupReviewRes {

    private Long reviewId;
    private String username;
    private String content;
    private String emoticon;
    private List<String> urls;
}
