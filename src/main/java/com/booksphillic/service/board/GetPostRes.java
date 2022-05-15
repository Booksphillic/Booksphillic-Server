package com.booksphillic.service.board;

import com.booksphillic.domain.bookstore.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetPostRes {

    private Long postId;
    private Long storeId;
    private String editorName;
    private String title;
    private String content;
    private List<Tag> tagList;
}
