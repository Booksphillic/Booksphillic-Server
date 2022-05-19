package com.booksphillic.service.bookstore.dto;

import com.booksphillic.domain.bookstore.BookstoreImage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookstoreInternalImgRes {
    private String url;

    public BookstoreInternalImgRes(BookstoreImage bookstoreImage) {
        url = bookstoreImage.getUrl();
    }
}
