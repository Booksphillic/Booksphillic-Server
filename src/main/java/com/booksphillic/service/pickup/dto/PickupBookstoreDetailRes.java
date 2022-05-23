package com.booksphillic.service.pickup.dto;

import com.booksphillic.domain.bookstore.Bookstore;
import com.booksphillic.domain.bookstore.OperatingHours;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PickupBookstoreDetailRes {

    private Long storeId;

    private String contact;

    private String address;

    private OperatingHours hours;

    public PickupBookstoreDetailRes(Bookstore bookstore) {
        storeId = bookstore.getId();
        contact = bookstore.getContact();
        address = bookstore.getAddress().toString();
        hours = bookstore.getHours();
    }
}
