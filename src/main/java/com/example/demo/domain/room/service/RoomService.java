package com.example.demo.domain.room.service;

import com.example.demo.domain.room.dto.request.RoomRequest;
import com.example.demo.domain.room.dto.response.RoomPageResponse;
import com.example.demo.domain.room.dto.response.RoomResponse;
import com.example.demo.domain.room.dto.response.RoomsResponse;
import com.example.demo.domain.room.entity.Room;
import com.example.demo.domain.room.repository.RoomRepositoy;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepositoy roomRepositoy;
    private final UserRepository userRepository;

    @Transactional
    public void saveRoom(RoomRequest roomRequest) {
        User user = userRepository.findById(roomRequest.getUserId()).get();
        roomRepositoy.save(new Room(user, roomRequest.getRoomType(), roomRequest.getTitle()));
    }

    public RoomPageResponse findRooms(int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Room> roomPage = roomRepositoy.findAll(pageable);

        List<RoomsResponse> roomsResponse = roomPage.getContent().stream().map(Room::createRoomsResponse).toList();
        return new RoomPageResponse((int) roomPage.getTotalElements(), roomPage.getTotalPages(), roomsResponse);
    }
    public RoomResponse findRoom(int id){
        Room room = roomRepositoy.findById(id).get();

        return room.createRoomResponse();
    }
}
