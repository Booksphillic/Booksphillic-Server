package com.booksphillic.service.board;

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
