package com.example.demo.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
public class InitUserResponse {
    private String status;
    private int code;
    private int total;
    private List<UserData> data;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserData {
        private int id;
        private String username;
        private String email;
    }
}
