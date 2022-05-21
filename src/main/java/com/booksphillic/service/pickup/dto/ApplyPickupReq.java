package com.booksphillic.service.pickup.dto;

import com.booksphillic.domain.pickup.BookGenre;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ApplyPickupReq {

    private Long userId;
    private Long storeId;
    private BookGenre bookGenre;
    private LocalDateTime pickupDate;
    private String status;
    private String requirements;

}
