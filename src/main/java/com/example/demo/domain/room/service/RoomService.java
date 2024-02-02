package com.example.demo.domain.room.service;

import com.example.demo.domain.room.dto.request.RoomRequest;
import com.example.demo.domain.room.dto.response.RoomPageResponse;
import com.example.demo.domain.room.dto.response.RoomResponse;
import com.example.demo.domain.room.dto.response.RoomsResponse;
import com.example.demo.domain.room.entity.Room;
import com.example.demo.domain.room.entity.RoomStatus;
import com.example.demo.domain.room.repository.RoomRepositoy;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.entity.UserStatus;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.domain.userRoom.dto.request.UserIdRequest;
import com.example.demo.domain.userRoom.repository.UserRoomRepository;
import com.example.demo.domain.userRoom.service.UserRoomService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepositoy roomRepositoy;
    private final UserRepository userRepository;
    private final UserRoomRepository userRoomRepository;
    private final AsyncService asyncService;
    private final UserRoomService userRoomService;


    @Transactional
    public void saveRoom(RoomRequest roomRequest) {
        User user = userRepository.findById(roomRequest.getUserId()).orElseThrow(EntityNotFoundException::new);

        if(user.getStatus() != UserStatus.ACTIVE | user.getUserRoom() != null){
            throw new IllegalStateException();
        }

        Room room = roomRepositoy.save(new Room(user, roomRequest.getRoomType(), roomRequest.getTitle()));
        userRoomService.joinRoom(roomRequest.getUserId(),room.getId());
    }

    public RoomPageResponse findRooms(int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Room> roomPage = roomRepositoy.findAll(pageable);

        List<RoomsResponse> roomsResponse = roomPage.getContent().stream().map(Room::createRoomsResponse).toList();
        return new RoomPageResponse((int) roomPage.getTotalElements(), roomPage.getTotalPages(), roomsResponse);
    }

    public RoomResponse findRoom(int id) {
        Room room = roomRepositoy.findById(id).orElseThrow(EntityNotFoundException::new);

        return room.createRoomResponse();
    }

    @Transactional
    public void startGame(UserIdRequest userIdRequest, int roomId) throws InterruptedException, EntityNotFoundException {
        Room room = roomRepositoy.findById(roomId).orElseThrow(EntityNotFoundException::new);
        Integer amount = userRoomRepository.countByRoomId(room);
        User user = userRepository.findById(userIdRequest.getUserId()).orElseThrow(EntityNotFoundException::new);

        if (room.getStatus() == RoomStatus.WAIT
                && Objects.equals(room.getHost(), user)
                && Objects.equals(room.getRoomType().getTotal(), amount)) {
            room.setStatusProgress();
        }
        roomRepositoy.save(room);

        asyncService.scheduleEndGame(room);
    }


}
