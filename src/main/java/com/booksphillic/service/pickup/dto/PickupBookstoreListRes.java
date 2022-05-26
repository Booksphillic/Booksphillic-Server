package com.booksphillic.service.pickup.dto;

import com.booksphillic.domain.bookstore.Bookstore;
import com.booksphillic.domain.bookstore.OperatingHours;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PickupBookstoreListRes {

    private Long storeId;
    private String name;
    private String profileImgUrl;
    private String subtitle;
    private String district;
    private String address;
    private String contact;
    private String website;
    private OperatingHours hours;

    public PickupBookstoreListRes(Bookstore bookstore) {
        storeId = bookstore.getId();
        name = bookstore.getName();
        profileImgUrl = bookstore.getProfileImgUrl();
        subtitle = bookstore.getSubtitle();
        district = bookstore.getAddress().getDistrict().getKo().substring(0, 2);
        address = bookstore.getAddress().toString();
        contact = bookstore.getContact();
        website = bookstore.getWebsite();
        hours = bookstore.getHours();
    }

}
