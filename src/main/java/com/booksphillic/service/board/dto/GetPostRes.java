package com.booksphillic.service.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@Builder
public class GetPostRes {

    private Long postId;
    private String editorName;
    private String editorImage;
    private LocalDateTime createdAt;
    private String title;
    private String content;
    private List<String> tagList;
    private BookstoreInfo bookstore;
}
