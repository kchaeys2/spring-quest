package com.example.demo.domain.user.dto.response;

import com.example.demo.domain.user.entity.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private int id;
    private int fakerId;
    private String name;
    private String email;
    private UserStatus status;
    @Schema(description = "생성 일자", pattern = "yyyy-MM-dd HH:mm:ss", example = "2024-02-02 15:35:00")
    private String createdAt;
    @Schema(description = "수정 일자", pattern = "yyyy-MM-dd HH:mm:ss", example = "2024-02-20 15:35:00")
    private String updatedAt;
}
