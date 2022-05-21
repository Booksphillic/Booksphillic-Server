package com.booksphillic.service.pickup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApplyPickupReq {

    private Long userId;
    private Long storeId;
    private String genre;
    private LocalDateTime creationAt;
    private String status;
    private String requirements;

}
