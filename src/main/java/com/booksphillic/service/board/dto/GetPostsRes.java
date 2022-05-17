package com.booksphillic.service.board.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetPostsRes {
    private Long postId;
    private String storeImgUrl;
    private String title;
    private String content;
    private String district;
    private String editorName;
}
