package com.example.demo.domain.room.service;

import com.example.demo.domain.room.entity.Room;
import com.example.demo.domain.room.repository.RoomRepositoy;
import com.example.demo.domain.userRoom.service.UserRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AsyncService {
    private final RoomRepositoy roomRepositoy;
    private final UserRoomService userRoomService;

    @Async
    @Transactional
    public void scheduleEndGame(Room room) throws InterruptedException {
        TimeUnit.SECONDS.sleep(60);
        userRoomService.removeAllUsersAndFinish(room);
        roomRepositoy.save(room);
    }
}
