package com.example.demo.domain.userRoom.repository;

import com.example.demo.domain.userRoom.entity.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRoomRepository extends JpaRepository<UserRoom,Integer> {
}
