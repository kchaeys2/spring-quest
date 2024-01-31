package com.example.demo.domain.room.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RoomPageResponse {
    private int totalElements;
    private int totalPages;
    private List<RoomsResponse> roomList;
}
