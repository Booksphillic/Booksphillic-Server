package com.booksphillic.service.bookstore.dto;

import com.booksphillic.domain.bookstore.Bookstore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class BookstoreListRes {

    private Long storeId;
    private String name;
    private String profileImgUrl;
    private String subtitle;
    private String district;

    public BookstoreListRes(Bookstore bookstore) {
        storeId = bookstore.getId();
        name = bookstore.getName();
        profileImgUrl = bookstore.getProfileImgUrl();
        subtitle = bookstore.getSubtitle();
        district = bookstore.getAddress().getDistrict().getKo().substring(0,2);
    }
}
