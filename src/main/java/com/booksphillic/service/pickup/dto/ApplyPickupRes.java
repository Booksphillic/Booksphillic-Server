package com.booksphillic.service.pickup.dto;

import com.booksphillic.domain.pickup.BookGenre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ApplyPickupRes {

    private String bookstoreName;
    private LocalDateTime pickupDate;
    private BookGenre bookGenres;
    private String requirements;

}
