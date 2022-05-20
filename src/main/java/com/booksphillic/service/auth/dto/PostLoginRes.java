package com.booksphillic.service.auth.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@Builder
public class PostLoginRes {
    private Long userId;
    private String accessToken;
}
