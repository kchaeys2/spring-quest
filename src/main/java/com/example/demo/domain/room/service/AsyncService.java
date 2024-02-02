package com.example.demo.domain.room.service;

import com.example.demo.domain.room.entity.Room;
import com.example.demo.domain.room.repository.RoomRepositoy;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AsyncService {
    private final RoomRepositoy roomRepositoy;

    @Async
    public void scheduleEndGame(Room room) throws InterruptedException {
        TimeUnit.SECONDS.sleep(60);
        room.setStatusFinish();
        roomRepositoy.save(room);
    }
}
