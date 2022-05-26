package com.booksphillic.controller;

import com.booksphillic.domain.board.Post;
import com.booksphillic.domain.bookstore.Bookstore;
import com.booksphillic.repository.PostRepository;
import com.booksphillic.repository.tag.BookstoreTagRepository;
import com.booksphillic.response.BaseException;
import com.booksphillic.response.BaseResponse;
import com.booksphillic.service.board.PostService;
import com.booksphillic.service.tag.TagService;
import com.booksphillic.service.tag.dto.GetStorePostsRes;
import com.booksphillic.service.tag.dto.GetTagSearchRes;
import com.booksphillic.service.tag.dto.GetTagStoresRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;
    private final PostService postService;

    @GetMapping("")
    public BaseResponse<GetTagSearchRes> searchTag(@RequestParam String name) {
        try{
            List<GetTagStoresRes> stores = tagService.getTagStore(name);
            List<GetStorePostsRes> posts = new ArrayList<>();
            for(GetTagStoresRes store : stores) {
                posts.addAll(postService.getStorePosts(store.getStoreId()));
            }
            return new BaseResponse<>(GetTagSearchRes.builder()
                    .stores(stores)
                    .posts(posts)
                    .build());
        } catch (BaseException e) {
            return new BaseResponse<>(e.getCode());
        }
    }
}
