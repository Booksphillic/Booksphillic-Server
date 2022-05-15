package com.booksphillic.domain.response;

import lombok.Getter;

@Getter
public enum BaseResponseCode {

    SUCCESS(1000, "성공하였습니다."),

    INVALID_DISTRICT(2000, "잘못된 지역구(명)입니다."),

    DATABASE_ERROR(4000, "데이터베이스 연결에 실패하였습니다.");

    private final int code;
    private final String message;

    private BaseResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
