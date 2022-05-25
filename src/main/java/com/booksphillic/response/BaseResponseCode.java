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
    SCRAP_ERROR(2102, "스크랩에 실패하였습니다. 다시 시도해주세요"),
    INVALID_PICKUP_ID(2103, "존재하지 않은 픽업입니다."),

    INVALID_FORMATTED_EMAIL(2200, "올바른 이메일 형식이 아닙니다."),
    NON_EXISTENT_EMAIL(2201, "존재하지 않는 이메일입니다."),
    WRONG_PASSWORD(2202, "잘못된 비밀번호입니다."),
    EMPTY_ACCESS_TOKEN(2203, "Access Token을 추가해주세요."),
    EXPIRED_TOKEN(2204, "만료된 토큰입니다."),
    UNAUTHORIZED_USER(2205, "권한이 없는 회원입니다."),
    INVALID_ACCESS_TOKEN(2206, "올바른 토큰이 아닙니다."),
    DISAGREE_REQUIRED_ITEMS(2207, "필수 항목을 모두 동의해주세요"),

    PASSWORD_CONFIRMATION_DIFFERENT(2210, "비밀번호가 일치하지 않습니다."),
    DUPLICATED_EMAIL(2211, "중복된 이메일입니다."),
    INVALID_FORMATTED_PHONE_NUMBER(2212, "올바른 전화번호 형식이 아닙니다."),


    DATABASE_ERROR(4000, "데이터베이스 요청에 실패하였습니다.");



    private final int code;
    private final String message;

    private BaseResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
