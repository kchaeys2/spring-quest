package com.example.demo.domain.room.dto.response;

import com.example.demo.domain.room.entity.RoomStatus;
import com.example.demo.domain.room.entity.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class RoomResponse extends RoomsResponse {
    @Schema(description = "생성 일자", pattern = "yyyy-MM-dd HH:mm:ss", example = "2024-02-20 15:35:00")
    private final String createAt;
    @Schema(description = "수정 일자", pattern = "yyyy-MM-dd HH:mm:ss", example = "2024-02-20 15:35:00")
    private final String updateAt;

    public RoomResponse(int id, String title, int hostId, RoomType roomType, RoomStatus status,
                        String createAt, String updateAt) {
        super(id, title, hostId, roomType, status);
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
