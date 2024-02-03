package com.example.demo.domain.room.dto.request;

import com.example.demo.domain.room.entity.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class RoomRequest {
    @Schema(description = "방 생성하는 유저Id")
    private int userId;
    @Schema(description = "단식,복식 선택")
    private RoomType roomType;
    @Schema(description = "방 이름")
    private String title;
}
