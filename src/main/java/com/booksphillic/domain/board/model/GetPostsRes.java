package com.booksphillic.domain.board.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class GetPostsRes {
    private Long postId;
    private String title;
    private String content;
    private String district;
    private String editorName;
}
