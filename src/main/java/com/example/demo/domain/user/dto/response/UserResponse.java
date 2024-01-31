package com.example.demo.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private int id;
    private int fakerId;
    private String name;
    private String email;
    private String status;
    private String createdAt;
    private String updatedAt;
}
