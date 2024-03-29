package com.example.demo.domain.userRoom.service;

import com.example.demo.domain.room.entity.Room;
import com.example.demo.domain.room.entity.RoomStatus;
import com.example.demo.domain.room.repository.RoomRepositoy;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.entity.UserStatus;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.domain.userRoom.dto.request.UserIdRequest;
import com.example.demo.domain.userRoom.entity.Team;
import com.example.demo.domain.userRoom.entity.UserRoom;
import com.example.demo.domain.userRoom.repository.UserRoomRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public void joinRoom(int userId, int roomId) {
        Room room = roomRepositoy.findById(roomId).orElseThrow(NullPointerException::new);
        User user = userRepository.findById(userId).orElseThrow(NullPointerException::new);

        if (!checkConditions(user, room)) {
            throw new IllegalArgumentException("참가할 수 없습니다.");
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
    public void leaveRoom(UserIdRequest userIdRequest, int roomId)
            throws EntityNotFoundException, IllegalStateException {
        Room room = roomRepositoy.findById(roomId).orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findById(userIdRequest.getUserId()).orElseThrow(EntityNotFoundException::new);

        validateRoomStatus(room);

        if (room.getHost() == user) {
            removeAllUsersAndFinish(room);
        } else {
            leaveUserFromRoom(room, user);
        }
    }

    private void validateRoomStatus(Room room) {
        if (room.getStatus() == RoomStatus.FINISH || room.getStatus() == RoomStatus.PROGRESS) {
            throw new IllegalStateException("이미 시작(PROGRESS) 상태인 방이거나 끝난(FINISH) 상태의 방은 나갈 수 없습니다");
        }
    }

    public void removeAllUsersAndFinish(Room room) {
        List<UserRoom> userRooms = userRoomRepository.findAllByRoomId(room);
        userRooms.forEach(userRoom -> {
            userRoom.delete();
            userRoomRepository.delete(userRoom);
        });
        room.setStatusFinish();
    }

    private void leaveUserFromRoom(Room room, User user) {
        UserRoom userRoom = userRoomRepository.findByRoomIdAndUserId(room, user)
                .orElseThrow(NullPointerException::new);
        userRoom.delete();
        userRoomRepository.delete(userRoom);
    }

    @Transactional
    public void changeTeam(UserIdRequest userIdRequest, int roomId) {
        Room room = roomRepositoy.findById(roomId).orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findById(userIdRequest.getUserId()).orElseThrow(EntityNotFoundException::new);
        UserRoom userRoom = userRoomRepository.findByRoomIdAndUserId(room, user).orElseThrow(EntityNotFoundException::new);

        Team changeTeam = (userRoom.getTeam() == Team.RED) ? Team.BLUE : Team.RED;

        validateTeamChange(room, changeTeam);

        userRoom.setTeam(changeTeam);
    }

    private void validateTeamChange(Room room, Team changeTeam) {
        if ((room.getRoomType().getTotal() / 2) == userRoomRepository.countUserRoomsByRoomIdAndTeam(room, changeTeam)) {
            throw new IllegalStateException(changeTeam.name() + "팀에 추가 인원이 들어올 수 없습니다.");
        }

        if (room.getStatus() != RoomStatus.WAIT) {
            throw new IllegalStateException("대기 상태의 방이 아닙니다.");
        }
    }
}
