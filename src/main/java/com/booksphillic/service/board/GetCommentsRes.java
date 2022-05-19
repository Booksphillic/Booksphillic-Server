package com.booksphillic.service.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class GetCommentsRes {

    private String username;
    private String userProfileImgUrl;
    private String content;
    private LocalDateTime createdAt;

}
