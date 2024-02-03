package com.example.demo.domain.userRoom.controller;

import com.example.demo.domain.userRoom.dto.request.UserIdRequest;
import com.example.demo.domain.userRoom.service.UserRoomService;
import com.example.demo.global.ApiResponse;
import com.example.demo.global.ApiResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
@Tag(name = "UserRoom API", description = "User와 Room 상호작용 관련 API")
public class TeamController {
    private final UserRoomService userRoomService;

    @PutMapping("/{roomId}")
    @Operation(summary = "팀 변경 API")
    public ApiResponse<ApiResponseStatus> changeTeam(@RequestBody UserIdRequest userIdRequest,
                                                     @PathVariable int roomId) {
        try {
            userRoomService.changeTeam(userIdRequest, roomId);
            return new ApiResponse<>(ApiResponseStatus.SUCCESS);
        } catch (EntityNotFoundException | IllegalStateException exception) {
            return new ApiResponse<>(ApiResponseStatus.WRONG);
        }
    }
}
