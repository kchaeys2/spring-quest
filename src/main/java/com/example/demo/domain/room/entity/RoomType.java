package com.example.demo.domain.room.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoomType {
    SINGLE(1, 1),
    DOUBLE(2, 2);

    private final Integer red;
    private final Integer blue;
    private final Integer total;

    RoomType(Integer red, Integer blue) {
        this.red = red;
        this.blue = blue;
        this.total = red + blue;
    }
}