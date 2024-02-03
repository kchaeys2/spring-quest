package com.example.demo.domain.room.dto.response;

import com.example.demo.domain.room.entity.RoomStatus;
import com.example.demo.domain.room.entity.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomsResponse {
    private int id;
    private String title;
    private int hostId;
    @Schema(allowableValues = {"SINGLE", "DOUBLE"})
    private RoomType roomType;
    @Schema(allowableValues = {"WAIT", "PROGRESS", "FINISH"})
    private RoomStatus status;
}
