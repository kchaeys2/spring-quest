package com.example.demo.domain.room.controller;

import com.example.demo.domain.room.dto.request.RoomRequest;
import com.example.demo.domain.room.dto.response.RoomPageResponse;
import com.example.demo.domain.room.dto.response.RoomResponse;
import com.example.demo.domain.room.service.RoomService;
import com.example.demo.domain.userRoom.dto.request.UserIdRequest;
import com.example.demo.global.ApiResponse;
import com.example.demo.global.ApiResponseStatus;
import com.example.demo.global.SwaggerExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Operation(summary = "방 생성 API")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS_RESPONSE)
            )
    )
    public ApiResponse<ApiResponseStatus> saveRoom(@RequestBody RoomRequest roomRequest) {
        try {
            roomService.saveRoom(roomRequest);
            return new ApiResponse<>(ApiResponseStatus.SUCCESS);
        } catch (EntityNotFoundException | IllegalStateException exception) {
            return new ApiResponse<>(ApiResponseStatus.WRONG);
        }
    }

    @GetMapping("")
    @Operation(summary = "방 전체 조회 API")
    public ApiResponse<RoomPageResponse> findRooms(@RequestParam int size, @RequestParam int page) {
        return new ApiResponse<>(roomService.findRooms(size, page));
    }

    @GetMapping("/{roomId}")
    @Operation(summary = "방 상세 조회 API")
    public ApiResponse<RoomResponse> findRoom(@PathVariable int roomId) {
        try {
            return new ApiResponse<>(roomService.findRoom(roomId));
        } catch (EntityNotFoundException exception) {
            return new ApiResponse<>(ApiResponseStatus.WRONG);
        }
    }

    @PutMapping("/start/{roomId}")
    @Operation(summary = "게임시작 API", description = "게임 시작 후 1분 후에 자동으로 게임이 종료됩니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS_RESPONSE)
            )
    )
    public ApiResponse<ApiResponseStatus> progressGame(@RequestBody UserIdRequest userIdRequest,
                                                       @PathVariable int roomId) {
        try {
            roomService.startGame(userIdRequest, roomId);
            return new ApiResponse<>(ApiResponseStatus.SUCCESS);
        } catch (InterruptedException | EntityNotFoundException | IllegalStateException exception) {
            return new ApiResponse<>(ApiResponseStatus.WRONG);
        }
    }
}
