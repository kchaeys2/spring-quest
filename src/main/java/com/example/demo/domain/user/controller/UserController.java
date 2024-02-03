package com.example.demo.domain.user.controller;

import com.example.demo.domain.user.dto.request.InitUserRequest;
import com.example.demo.domain.user.dto.response.InitUserResponse;
import com.example.demo.domain.user.dto.response.UserPageResponse;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.global.ApiResponse;
import com.example.demo.global.ApiResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ResponseBody
    @PostMapping("/init")
    @Operation(summary = "초기화 API", description = "모든 데이터를 삭제하고 외부에서 User를 받아 저장")
    public ApiResponse<ApiResponseStatus> saveUser(@RequestBody InitUserRequest initUserRequest) {
        OkHttpClient client = new OkHttpClient();

        String apiUrl = "https://fakerapi.it/api/v1/users?_seed=" + initUserRequest.getSeed() + "&_quantity=" + initUserRequest.getQuantity() + "&_locale=ko_KR";

        Request request = new Request.Builder().url(apiUrl).build();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Response response = client.newCall(request).execute();

            String responseData = Objects.requireNonNull(response.body()).string();

            InitUserResponse myResponse = objectMapper.readValue(responseData, InitUserResponse.class);
            userService.saveUser(myResponse.getData());
            return new ApiResponse<>(ApiResponseStatus.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(ApiResponseStatus.FAIL);
        }
    }

    @GetMapping("/user")
    @Operation(summary = "유저 전체 조회 API")
    public ApiResponse<UserPageResponse> findUsers(@RequestParam int size, @RequestParam int page) {
        return new ApiResponse<>(userService.findUsers(page, size));
    }
}
