package com.example.demo.domain.room.service;

import com.example.demo.domain.room.dto.request.RoomRequest;
import com.example.demo.domain.room.entity.Room;
import com.example.demo.domain.room.repository.RoomRepositoy;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
}
