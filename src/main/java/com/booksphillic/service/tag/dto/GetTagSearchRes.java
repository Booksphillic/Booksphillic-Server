package com.booksphillic.service.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class GetTagSearchRes {

    private List<GetTagStoresRes> stores;
    private List<GetStorePostsRes> posts;
}
