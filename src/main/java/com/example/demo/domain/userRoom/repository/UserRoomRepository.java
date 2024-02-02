package com.example.demo.domain.userRoom.repository;

import com.example.demo.domain.room.entity.Room;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.userRoom.entity.Team;
import com.example.demo.domain.userRoom.entity.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRoomRepository extends JpaRepository<UserRoom, Integer> {
    Optional<UserRoom> findByRoomIdAndUserId(Room roomId, User userId);

    List<UserRoom> findAllByRoomId(Room roomId);

    Integer countUserRoomsByRoomIdAndTeam(Room room, Team team);

    boolean existsByUserId(User userId);

    Integer countByRoomId(Room room);
}
