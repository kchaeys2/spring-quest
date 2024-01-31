package com.example.demo.domain.room.dto.request;

import lombok.Getter;

@Getter
public class RoomRequest {
    private int userId;
    private String roomType;
    private String title;
}
