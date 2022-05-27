package com.booksphillic.service.pickup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PickupReviewListRes {

    private Long reviewId;
    private String username;
    private String content;
    private String emoticon;
    private LocalDateTime createdAt;
    private String url;

}
