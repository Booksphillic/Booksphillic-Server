package com.booksphillic.service.board.dto;

import com.booksphillic.domain.board.PostCategory;
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
    private PostCategory category;
    private String title;
    private List<String> content;
    private List<String> contentImages;
    private List<String> bookstoreImages;
    private List<String> tagList;
    private BookstoreInfo bookstore;
}
