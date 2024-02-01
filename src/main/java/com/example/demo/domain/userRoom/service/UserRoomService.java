package com.example.demo.domain.userRoom.service;

import com.example.demo.domain.room.entity.Room;
import com.example.demo.domain.room.entity.RoomStatus;
import com.example.demo.domain.room.repository.RoomRepositoy;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.entity.UserStatus;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.domain.userRoom.dto.request.RoomRequest;
import com.example.demo.domain.userRoom.entity.Team;
import com.example.demo.domain.userRoom.entity.UserRoom;
import com.example.demo.domain.userRoom.repository.UserRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserRoomService {
    private final UserRoomRepository userRoomRepository;
    private final RoomRepositoy roomRepositoy;
    private final UserRepository userRepository;

    @Transactional
    public void joinRoom(RoomRequest roomRequest, int roomId) {
        Room room = roomRepositoy.findById(roomId).orElseThrow(NullPointerException::new);
        User user = userRepository.findById(roomRequest.getUserId()).orElseThrow(NullPointerException::new);

        if (!checkConditions(user, room)) {
            throw new IllegalArgumentException();
        }

        Team team = (checkJoinRedAble(room)) ? Team.RED : Team.BLUE;
        userRoomRepository.save(new UserRoom(room, user, team));
    }

    private boolean checkConditions(User user, Room room) {
        return !userRoomRepository.existsByUserId(user) &&
                user.getStatus() == UserStatus.ACTIVE &&
                room.getStatus() == RoomStatus.WAIT &&
                room.checkJoinUserAble();
    }

    private boolean checkJoinRedAble(Room room) {
        long redAmount = userRoomRepository.countUserRoomsByRoomIdAndTeam(room, Team.RED);
        return room.getRoomType().getRed() > redAmount;
    }


    @Transactional
    public void leaveRoom(RoomRequest roomRequest, int roomId) {
        Room room = roomRepositoy.findById(roomId).get();
        User user = userRepository.findById(roomRequest.getUserId()).get();

        UserRoom userRoom = userRoomRepository.findByRoomIdAndUserId (room, user);
        userRoom.delete();
        userRoomRepository.delete(userRoom);
    }
}
