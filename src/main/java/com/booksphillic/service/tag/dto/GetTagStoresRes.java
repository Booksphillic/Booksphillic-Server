package com.booksphillic.service.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GetTagStoresRes {

    private Long storeId;
    private String name;
    private String description;
    private String district;
    private String profileImg;

}
