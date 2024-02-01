package com.example.demo.domain.userRoom.repository;

import com.example.demo.domain.room.entity.Room;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.userRoom.entity.Team;
import com.example.demo.domain.userRoom.entity.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRoomRepository extends JpaRepository<UserRoom,Integer> {
    UserRoom findByRoomIdAndUserId(Room roomId, User userId);
    Integer countUserRoomsByRoomIdAndTeam(Room room, Team team);
    boolean existsByUserId(User userId);
}
