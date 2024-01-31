package com.example.demo.domain.room.controller;

import com.example.demo.domain.room.dto.request.RoomRequest;
import com.example.demo.domain.room.service.RoomService;
import com.example.demo.global.ApiResponse;
import com.example.demo.global.ApiResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @ResponseBody
    @PostMapping("")
    public ApiResponse<ApiResponseStatus> saveRoom(@RequestBody RoomRequest roomRequest) {
        roomService.saveRoom(roomRequest);
        return new ApiResponse<>(ApiResponseStatus.SUCCESS);
    }
}
