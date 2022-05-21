package com.booksphillic.service.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class GetScrapRes {

    private Long scrapId;
    private String bookstore;       //서점 이름
    private String subtitle;        //서점 소제목
    private List<String> tags;      //서점 태그 리스트
    private String profileImgUrl;   //서점 대표 이미지
}
