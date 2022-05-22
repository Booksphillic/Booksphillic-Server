package com.booksphillic.service.pickup.dto;

import com.booksphillic.domain.pickup.BookGenre;
import com.booksphillic.domain.pickup.PickupStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ApplyPickupReq {

    private Long userId;
    private Long storeId;
    private String bookGenre;
    private LocalDateTime pickupDate;
    private PickupStatus status;
    private String requirements;

}
