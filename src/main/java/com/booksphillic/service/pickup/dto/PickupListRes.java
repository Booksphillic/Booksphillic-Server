package com.booksphillic.service.pickup.dto;

import com.booksphillic.domain.pickup.BookGenre;
import com.booksphillic.domain.pickup.Pickup;
import com.booksphillic.domain.pickup.PickupStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PickupListRes {
    private Long pickupId;
    private Long storeId;
    private String bookstore;
    private BookGenre bookGenre;
    private PickupStatus pickupStatus;
    private String requirements;
    private LocalDateTime createdAt;
    private boolean review; // 해당 픽업에 리뷰를 작성했는지 아닌지

    public PickupListRes(Pickup pickup) {
        pickupId = pickup.getId();
        storeId = pickup.getStoreId();
        bookGenre = pickup.getBookGenre();
        pickupStatus = pickup.getPickupStatus();
        requirements = pickup.getRequirements();
        createdAt = pickup.getCreatedAt();

    }

}
