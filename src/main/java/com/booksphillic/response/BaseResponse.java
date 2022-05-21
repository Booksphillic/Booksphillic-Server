package com.booksphillic.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class BaseResponse<T> {

    private final int code;
    private final String message;

    // null인 경우 제외
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public BaseResponse() {
        this.code = BaseResponseCode.SUCCESS.getCode(); //성공
        this.message = BaseResponseCode.SUCCESS.getMessage();
    }

    // 성공한 경우
    public BaseResponse(T data) {
        this.code = BaseResponseCode.SUCCESS.getCode(); //성공
        this.message = BaseResponseCode.SUCCESS.getMessage();
        this.data = data;
    }

    // 실패한 경우
    public BaseResponse(BaseResponseCode status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }
}
