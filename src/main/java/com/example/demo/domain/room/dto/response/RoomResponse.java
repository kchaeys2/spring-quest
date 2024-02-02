package com.example.demo.domain.room.dto.response;

import com.example.demo.domain.room.entity.RoomStatus;
import com.example.demo.domain.room.entity.RoomType;
import lombok.Getter;

@Getter
public class RoomResponse extends RoomsResponse{
    private final String createAt;
    private final String updateAt;

    public RoomResponse(int id, String title, int hostId, RoomType roomType, RoomStatus status,
                        String createAt, String updateAt) {
        super(id, title, hostId, roomType, status);
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
