package com.booksphillic.service.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetReviewRes {

    // 서점 이름, 작성일, 내용, 리뷰 타입(책방/미스터리북), 이미지들, 이모티콘
    private String bookstore;
    private LocalDateTime createdAt;
    private String content;
    private String type;
    private List<String> urls;
    private String emoticon;

}
