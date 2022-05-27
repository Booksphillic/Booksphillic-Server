package com.booksphillic.service.pickup.dto;

import com.booksphillic.domain.pickup.PickupStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApplyPickupReq {

    private Long userId;
    private Long storeId;
    private String bookGenre;
    private LocalDateTime pickupDate;
    private PickupStatus status;
    private String requirements;

}
