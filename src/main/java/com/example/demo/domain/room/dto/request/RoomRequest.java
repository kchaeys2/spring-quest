package com.example.demo.domain.room.dto.request;

import com.example.demo.domain.room.entity.RoomType;
import lombok.Getter;

@Getter
public class RoomRequest {
    private int userId;
    private RoomType roomType;
    private String title;
}
