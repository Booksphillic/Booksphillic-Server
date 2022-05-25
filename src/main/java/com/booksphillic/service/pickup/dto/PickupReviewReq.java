package com.booksphillic.service.pickup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PickupReviewReq {

    private Long userId;
    private String content;
    private List<String> urls;
    private String emoticon;

}
