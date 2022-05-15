package com.booksphillic.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BaseException extends Exception{
    private BaseResponseCode code;
}
