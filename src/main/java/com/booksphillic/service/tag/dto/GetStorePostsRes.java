package com.booksphillic.service.tag.dto;

import com.booksphillic.domain.board.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GetStorePostsRes {

    private Long postId;
    private String title;
    private String content;
    private PostCategory category;
    private String district;
    private String profileImg;

}
