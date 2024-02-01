package com.example.demo.domain.userRoom.controller;

import com.example.demo.domain.userRoom.dto.request.RoomRequest;
import com.example.demo.domain.userRoom.service.UserRoomService;
import com.example.demo.global.ApiResponse;
import com.example.demo.global.ApiResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class UserRoomController {
    private final UserRoomService userRoomService;

    @ResponseBody
    @PostMapping("/attention/{roomId}")
    public ApiResponse<ApiResponseStatus> joinRoom(@RequestBody RoomRequest roomRequest,
                                                   @PathVariable int roomId) {

        try {
            userRoomService.joinRoom(roomRequest, roomId);
            return new ApiResponse<>(ApiResponseStatus.SUCCESS);
        }catch (NullPointerException | IllegalArgumentException exception){
            return new ApiResponse<>(ApiResponseStatus.WRONG);
        }
    }
    @ResponseBody
    @PostMapping("/out/{roomId}")
    public ApiResponse<ApiResponseStatus> leaveRoom(@RequestBody RoomRequest roomRequest,
                                                    @PathVariable int roomId){
        userRoomService.leaveRoom(roomRequest,roomId);
        return new ApiResponse<>(ApiResponseStatus.SUCCESS);
    }
}
