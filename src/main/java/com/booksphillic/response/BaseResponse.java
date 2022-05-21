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

    // 데이터가 있는 경우
    public BaseResponse(T data) {
        this.code = BaseResponseCode.SUCCESS.getCode(); //성공
        this.message = BaseResponseCode.SUCCESS.getMessage();
        this.data = data;
    }

    // 데이터가 없는 경우
    public BaseResponse(BaseResponseCode status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }

}
