package com.example.demo.domain.room.entity;

import com.example.demo.domain.BaseEntity;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.userRoom.entity.UserRoom;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@NoArgsConstructor
@Getter
public class Room extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host")
    private User host;
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(mappedBy = "roomId")
    private List<UserRoom> joinUsers;

    public Room(User user, String roomType, String title) {
        this.host = user;
        this.roomType = RoomType.valueOf(roomType);
        this.title = title;
        this.status = Status.WAIT;
    }

    enum Status {
        WAIT, PROGRESS, FINISH
    }
}
