package com.booksphillic.service.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetProfileRes {

    private String profileImgUrl;
    private String username;
    private String email;
    private String phoneNumber;
}
