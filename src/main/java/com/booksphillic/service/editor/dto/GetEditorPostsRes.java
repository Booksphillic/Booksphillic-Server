package com.booksphillic.service.editor.dto;

import com.booksphillic.domain.board.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GetEditorPostsRes {

    private Long postId;
    private String title;
    private PostCategory postCategory;
    private String storeImgUrl;
}
