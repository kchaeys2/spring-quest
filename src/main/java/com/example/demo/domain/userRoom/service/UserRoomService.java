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
    public void joinRoom(RoomRequest roomRequest, int roomId) throws IllegalArgumentException {
        Optional<Room> roomData = roomRepositoy.findById(roomId);
        Optional<User> userData = userRepository.findById(roomRequest.getUserId());
        Room room;
        User user;
        if (roomData.isPresent() && userData.isPresent()){
            room = roomData.get();
            user = userData.get();
        }else{
            throw new NullPointerException();
        }

        if (!checkAlreadyJoin(user) && checkUserStatus(user)
                && checkRoomStatus(room) && room.checkJoinUserAble()){
            if(checkJoinRedAble(room)){
                userRoomRepository.save(new UserRoom(room,user,Team.RED));
            }else{
                userRoomRepository.save(new UserRoom(room,user,Team.BLUE));
            }
        }else{
            throw new IllegalArgumentException();
        }
    }
    private boolean checkJoinRedAble(Room room){
        Integer redAmount = userRoomRepository.countUserRoomsByRoomIdAndTeam(room,Team.RED);

        return room.getRoomType().getRed() > redAmount;
    }
    private boolean checkRoomStatus(Room room){
        return room.getStatus() == RoomStatus.WAIT;
    }
    private boolean checkUserStatus(User user){
        return user.getStatus() == UserStatus.ACTIVE;
    }
    private boolean checkAlreadyJoin(User user){
        return userRoomRepository.existsByUserId(user);
    }

    @Transactional
    public void leaveRoom(RoomRequest roomRequest, int roomId) {
        Room room = roomRepositoy.findById(roomId).get();
        User user = userRepository.findById(roomRequest.getUserId()).get();

        userRoomRepository.deleteUserRoomByRoomIdAndUserId(room, user);
    }
}
