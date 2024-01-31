package com.example.demo.domain.userRoom.service;

import com.example.demo.domain.room.entity.Room;
import com.example.demo.domain.room.repository.RoomRepositoy;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.domain.userRoom.dto.request.RoomRequest;
import com.example.demo.domain.userRoom.entity.Team;
import com.example.demo.domain.userRoom.entity.UserRoom;
import com.example.demo.domain.userRoom.repository.UserRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserRoomService {
    private final UserRoomRepository userRoomRepository;
    private final RoomRepositoy roomRepositoy;
    private final UserRepository userRepository;

    @Transactional
    public void joinRoom(RoomRequest roomRequest, int roomId) {
        Room room = roomRepositoy.findById(roomId).get();
        User user = userRepository.findById(roomRequest.getUserId()).get();

        userRoomRepository.save(new UserRoom(room, user, Team.RED));
    }

    @Transactional
    public void leaveRoom(RoomRequest roomRequest, int roomId) {
        Room room = roomRepositoy.findById(roomId).get();
        User user = userRepository.findById(roomRequest.getUserId()).get();

        userRoomRepository.deleteUserRoomByRoomIdAndUserId(room, user);
    }
}
