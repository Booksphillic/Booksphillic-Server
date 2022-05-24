package com.booksphillic.service.pickup.dto;

import com.booksphillic.domain.pickup.BookGenre;
import com.booksphillic.domain.pickup.Pickup;
import com.booksphillic.domain.pickup.PickupStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PickupListRes {

    private Long storeId;
    private BookGenre bookGenre;
    private PickupStatus pickupStatus;
    private String requirements;

    public PickupListRes(Pickup pickup) {
        storeId = pickup.getStoreId();
        bookGenre = pickup.getBookGenre();
        pickupStatus = pickup.getPickupStatus();
        requirements = pickup.getRequirements();
    }

}
