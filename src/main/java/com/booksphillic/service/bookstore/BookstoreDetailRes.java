package com.booksphillic.service.bookstore;

import com.booksphillic.domain.bookstore.Address;
import com.booksphillic.domain.bookstore.Bookstore;
import com.booksphillic.domain.bookstore.BookstoreImage;
import com.booksphillic.domain.bookstore.OperatingHours;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Builder
public class BookstoreDetailRes {

    private Long storeId;
    private String name; //책방 이름
    private String profileImgUrl; //프로필 사진
    private String description; //소개글

    private OperatingHours hours; //운영 시간
    private String address; //위치(주소)
    private String website; //웹사이트 url (인스타, 페북 등)

    private List<BookstoreInternalImgRes> internalImgUrls; //책방 내부 이미지들

    public BookstoreDetailRes(Bookstore bookstore, List<BookstoreImage> bookstoreImages) {
        storeId = bookstore.getId();
        name = bookstore.getName();
        profileImgUrl = bookstore.getProfileImgUrl();
        description = bookstore.getDescription();
        hours = bookstore.getHours();
        address = bookstore.getAddress().toString();
        website = bookstore.getWebsite();
        internalImgUrls = bookstoreImages.stream()
                .map(bi -> new BookstoreInternalImgRes(bi))
                .collect(Collectors.toList());
    }
}
