package com.example.demo.global;

import lombok.Getter;

@Getter
public enum ApiResponseStatus {
    SUCCESS(200, "API 요청이 성공했습니다."),
    FAIL(500, "에러가 발생했습니다.");

    private final int code;
    private final String message;

    ApiResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
