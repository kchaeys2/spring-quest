package com.example.demo.domain.room.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomsResponse {
    private int id;
    private String title;
    private int hostId;
    private String roomType;
    private String status;
}
