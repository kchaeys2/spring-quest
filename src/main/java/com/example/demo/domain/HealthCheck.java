package com.example.demo.domain;

import com.example.demo.global.ApiResponse;
import com.example.demo.global.ApiResponseStatus;
import com.example.demo.global.SwaggerExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthCheck {
    @ResponseBody
    @GetMapping("")
    @Operation(summary = "헬스체크 API", description = "서버의 상태를 체크하는 API")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS_RESPONSE)
            )
    )
    public ApiResponse<ApiResponseStatus> checkHealth() {
        return new ApiResponse<>(ApiResponseStatus.SUCCESS);
    }
}
