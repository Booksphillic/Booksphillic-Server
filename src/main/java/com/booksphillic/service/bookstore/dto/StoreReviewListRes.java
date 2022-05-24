package com.booksphillic.service.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class StoreReviewListRes {

    private Long reviewId;
    private String username;
    private String content;
    private String emoticon;
    private LocalDateTime createdAt;
    private List<String> urls;
}
