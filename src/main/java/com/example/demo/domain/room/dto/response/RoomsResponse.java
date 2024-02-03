package com.example.demo.domain.room.dto.response;

import com.example.demo.domain.room.entity.RoomStatus;
import com.example.demo.domain.room.entity.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomsResponse {
    private int id;
    private String title;
    private int hostId;
    private RoomType roomType;
    private RoomStatus status;
}
