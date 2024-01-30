package com.example.demo.domain.userRoom.entity;

import com.example.demo.domain.room.entity.Room;
import com.example.demo.domain.user.entity.User;
import jakarta.persistence.*;

@Entity
@Table
public class UserRoom {
    @Id
    @GeneratedValue
    @Column( nullable = false)
    private Integer id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room roomId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;
    @Enumerated(EnumType.STRING)
    private Team team;
    enum Team{
        RED, BLUE
    }
}
