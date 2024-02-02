package com.example.demo.domain.userRoom.entity;

import com.example.demo.domain.room.entity.Room;
import com.example.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
public class UserRoom {
    @Id @GeneratedValue
    @Column(nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room roomId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;
    @Enumerated(EnumType.STRING)
    private Team team;

    public UserRoom(Room room, User user, Team team) {
        this.roomId = room;
        this.userId = user;
        this.team = team;
    }

    public void delete() {
        roomId.outUser(this);
        userId.outRoom();
    }
    public Team getTeam(){
        return team;
    }
    public void setTeam(Team team){
        this.team = team;
    }
}
