package com.example.demo.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserPageResponse {
    private int totalElements;
    private int totalPages;
    private List<UserResponse> userList;


}
