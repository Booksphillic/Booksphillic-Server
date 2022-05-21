package com.booksphillic.service.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostProfileImageReq {
    private Long userId;
    private String imageUrl;
}
