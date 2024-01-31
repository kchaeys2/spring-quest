package com.example.demo.domain;

import com.example.demo.global.ApiResponse;
import com.example.demo.global.ApiResponseStatus;
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
    public ApiResponse<ApiResponseStatus> checkHealth() {
        return new ApiResponse<>(ApiResponseStatus.SUCCESS);
    }
}
