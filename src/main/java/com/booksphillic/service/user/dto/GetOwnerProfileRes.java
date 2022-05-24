package com.booksphillic.service.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetOwnerProfileRes {
    /**
     *     기본정보
     *     책방 프로필 사진, 책방 이름, 부제목, 소개글
     *
     *     추가정보
     *     책방 태그, 주소, 연락처, url, 운영 시간, 기타
     *
     *     책방 사진
     */
    private String profileImgUrl;
    private String name;
    private String subtitle;
    private String description;

    private List<String> tags;
    private String address;
    private String contact;
    private String website;
    private String hours;
    private String facility;

    private List<String> internalImages;

}
