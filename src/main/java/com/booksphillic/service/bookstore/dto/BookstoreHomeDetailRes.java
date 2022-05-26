package com.booksphillic.service.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BookstoreHomeDetailRes {
    private Long storeId;
    private String name; //책방 이름
    private String profileImgUrl; //프로필 사진
    private String description; //소개글
    private String subtitle; //부제목
    private boolean scraped; //스크랩
}
