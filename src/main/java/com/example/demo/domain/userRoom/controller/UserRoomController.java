package com.example.demo.domain.userRoom.controller;

import com.example.demo.domain.userRoom.dto.request.UserIdRequest;
import com.example.demo.domain.userRoom.service.UserRoomService;
import com.example.demo.global.ApiResponse;
import com.example.demo.global.ApiResponseStatus;
import com.example.demo.global.SwaggerExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
@Tag(name = "UserRoom API", description = "User와 Room 상호작용 관련 API")
public class UserRoomController {
    private final UserRoomService userRoomService;

    @ResponseBody
    @PostMapping("/attention/{roomId}")
    @Operation(summary = "방 참가 API")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS_RESPONSE)
            )
    )
    public ApiResponse<ApiResponseStatus> joinRoom(@RequestBody UserIdRequest userIdRequest,
                                                   @PathVariable int roomId) {

        try {
            userRoomService.joinRoom(userIdRequest.getUserId(), roomId);
            return new ApiResponse<>(ApiResponseStatus.SUCCESS);
        } catch (NullPointerException | IllegalArgumentException exception) {
            return new ApiResponse<>(ApiResponseStatus.WRONG);
        } catch (Exception exception) {
            return new ApiResponse<>(ApiResponseStatus.FAIL);
        }
    }

    @ResponseBody
    @PostMapping("/out/{roomId}")
    @Operation(summary = "방 나가기 API", description = "호스트가 방을 나가면 참가자 모두가 나가고 방은 FINISH 상태가 됩니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            content = @Content(
                    schema = @Schema(implementation = ApiResponse.class),
                    examples = @ExampleObject(value = SwaggerExamples.SUCCESS_RESPONSE)
            )
    )
    public ApiResponse<ApiResponseStatus> leaveRoom(@RequestBody UserIdRequest userIdRequest,
                                                    @PathVariable int roomId) {
        try {
            userRoomService.leaveRoom(userIdRequest, roomId);
            return new ApiResponse<>(ApiResponseStatus.SUCCESS);
        } catch (EntityNotFoundException | IllegalStateException exception) {
            return new ApiResponse<>(ApiResponseStatus.WRONG);
        } catch (Exception exception) {
            return new ApiResponse<>(ApiResponseStatus.FAIL);
        }
    }
}
