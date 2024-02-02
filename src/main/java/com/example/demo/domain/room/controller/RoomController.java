package com.example.demo.domain.room.controller;

import com.example.demo.domain.room.dto.request.RoomRequest;
import com.example.demo.domain.room.dto.response.RoomPageResponse;
import com.example.demo.domain.room.dto.response.RoomResponse;
import com.example.demo.domain.room.service.RoomService;
import com.example.demo.domain.userRoom.dto.request.UserIdRequest;
import com.example.demo.global.ApiResponse;
import com.example.demo.global.ApiResponseStatus;
import jakarta.persistence.EntityNotFoundException;
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

    @GetMapping("")
    public ApiResponse<RoomPageResponse> findRooms(@RequestParam int size, @RequestParam int page) {
        return new ApiResponse<>(roomService.findRooms(size, page));
    }

    @GetMapping("/{roomId}")
    public ApiResponse<RoomResponse> findRoom(@PathVariable int roomId) {
        return new ApiResponse<>(roomService.findRoom(roomId));
    }

    @PutMapping("/start/{roomId}")
    public ApiResponse<ApiResponseStatus> progressGame(@RequestBody UserIdRequest userIdRequest, @PathVariable int roomId) {
        try {
            roomService.startGame(userIdRequest, roomId);
            return new ApiResponse<>(ApiResponseStatus.SUCCESS);
        } catch (InterruptedException | EntityNotFoundException exception) {
            return new ApiResponse<>(ApiResponseStatus.WRONG);
        }
    }
}
