package com.booksphillic.service.auth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ResetPasswordReq {

    @NotNull @Email
    private String email;

}
