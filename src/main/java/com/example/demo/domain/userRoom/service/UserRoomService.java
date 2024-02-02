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

import java.util.List;


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
    public void leaveRoom(RoomRequest roomRequest, int roomId) throws NullPointerException {
        Room room = roomRepositoy.findById(roomId).orElseThrow(NullPointerException::new);
        User user = userRepository.findById(roomRequest.getUserId()).orElseThrow(NullPointerException::new);

        validateRoomStatus(room);

        if(room.getHost() == user){
            removeAllUsersAndFinish(room);
        }else{
            leaveUserFromRoom(room,user);
        }
    }
    private void validateRoomStatus(Room room) {
        if (room.getStatus() == RoomStatus.FINISH || room.getStatus() == RoomStatus.PROGRESS) {
            throw new RuntimeException("이미 시작(PROGRESS) 상태인 방이거나 끝난(FINISH) 상태의 방은 나갈 수 없습니다");
        }
    }
    private void removeAllUsersAndFinish(Room room) {
        List<UserRoom> userRooms = userRoomRepository.findAllByRoomId(room);
        userRooms.forEach(userRoom -> {
            userRoom.delete();
            userRoomRepository.delete(userRoom);
        });
        room.setHost();
        room.setStatusFinish();
    }
    private void leaveUserFromRoom(Room room, User user) {
        UserRoom userRoom = userRoomRepository.findByRoomIdAndUserId(room, user)
                .orElseThrow(NullPointerException::new);
        userRoom.delete();
        userRoomRepository.delete(userRoom);
    }
}
