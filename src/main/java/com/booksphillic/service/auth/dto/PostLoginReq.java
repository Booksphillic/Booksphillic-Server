package com.booksphillic.service.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
//@AllArgsConstructor
@NoArgsConstructor
public class PostLoginReq {

    private String email;
    private String password;
}
