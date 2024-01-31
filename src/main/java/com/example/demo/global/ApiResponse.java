package com.example.demo.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import static com.example.demo.global.ApiResponseStatus.SUCCESS;

@Getter
public class ApiResponse<T> {
    private final Integer code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 요청에 성공한 경우
    public ApiResponse(T result) {
        this.message = SUCCESS.getMessage();
        this.code = SUCCESS.getCode();
        this.result = result;
    }

    // 요청에 실패한 경우
    public ApiResponse(ApiResponseStatus status) {
        this.message = status.getMessage();
        this.code = status.getCode();
    }
}
