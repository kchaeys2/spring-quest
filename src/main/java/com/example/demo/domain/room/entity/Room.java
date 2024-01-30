package com.example.demo.domain.room.entity;

import com.example.demo.domain.BaseEntity;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.userRoom.entity.UserRoom;
import jakarta.persistence.*;

@Entity
@Table
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
    enum RoomType{
        SINGLE,DOUBLE
    }
    @Enumerated(EnumType.STRING)
    private Status status;
    enum Status{
        WAIT,PROGRESS,FINISH
    }
    @OneToOne(mappedBy = "roomId")
    private UserRoom userRoom;
}
