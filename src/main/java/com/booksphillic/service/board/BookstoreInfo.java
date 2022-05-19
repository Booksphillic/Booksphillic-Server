package com.booksphillic.service.board;

import com.booksphillic.domain.bookstore.Bookstore;
import com.booksphillic.domain.bookstore.OperatingHours;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class BookstoreInfo {

    private Long storeId;
    private String name;
    private String address;
    private String contact;
    private String website;
    private OperatingHours operatingHours;

    public BookstoreInfo (Bookstore bookstore) {
        this.storeId = bookstore.getId();
        this.name = bookstore.getName();
        this.address = bookstore.getAddress().getCity() + " " +
                        bookstore.getAddress().getDistrict().getKo() + " " +
                        bookstore.getAddress().getStreet();
        this.contact = bookstore.getContact();
        this.website = bookstore.getWebsite();
        this.operatingHours = bookstore.getHours();
    }
}
