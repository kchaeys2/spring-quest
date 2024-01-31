package com.example.demo.domain.room.dto.response;

import lombok.Getter;

@Getter
public class RoomResponse extends RoomsResponse{
    private final String createAt;
    private final String updateAt;

    public RoomResponse(int id, String title, int hostId, String roomType, String status,
                        String createAt,String updateAt) {
        super(id, title, hostId, roomType, status);
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
