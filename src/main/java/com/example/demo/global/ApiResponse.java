package com.example.demo.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import static com.example.demo.global.ApiResponseStatus.SUCCESS;

@Getter
public class ApiResponse<T> {
    @Schema(example = "200")
    private final Integer code;
    @Schema(example = "API 요청이 성공했습니다.")
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
