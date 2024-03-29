package com.example.demo.domain.room.repository;

import com.example.demo.domain.room.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepositoy extends JpaRepository<Room, Integer> {
    Page<Room> findAll(Pageable pageable);
}
