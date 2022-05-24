package com.booksphillic.service.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class StoreReviewReq {

    private Long userId;
    private String content;
    private List<String> urls;
    private String emoticon;
}
