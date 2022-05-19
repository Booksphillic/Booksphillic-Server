package com.booksphillic.service.board.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCommentReq {

    private Long userId;
    private String comment;
}
