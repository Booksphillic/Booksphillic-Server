package com.booksphillic.service.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class StoreReviewRes {

    private Long reviewId;
    private String username;
    private List<String> urls;

}
