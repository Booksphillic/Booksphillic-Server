package com.booksphillic.response;

import lombok.Getter;

@Getter
public enum BaseResponseCode {

    SUCCESS(1000, "성공하였습니다."),

    INVALID_DISTRICT(2000, "잘못된 지역구(명)입니다."),

    INVALID_POST_ID(2001, "존재하지 않는 글입니다."),
    INVALID_USER_ID(2002, "유효하지 않은 회원 아이디입니다."),
    INVALID_EDITOR_ID(2003, "유효하지 않은 에디터 아이디입니다."),

    INVALID_BOOKSTOREID(2100, "존재하지 않는 서점입니다."),
    IMAGE_UPLOAD_ERROR(2101, "파일 변환 중 에러가 발생했습니다."),

    DATABASE_ERROR(4000, "데이터베이스 연결에 실패하였습니다.");



    private final int code;
    private final String message;

    private BaseResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
