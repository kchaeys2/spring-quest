package com.example.demo.domain.user.entity;


import com.example.demo.domain.BaseEntity;
import com.example.demo.domain.room.entity.Room;
import com.example.demo.domain.userRoom.entity.UserRoom;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
public class User extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;
    private Integer fakerId;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Status status;
    enum Status{
        WAIT,ACTIVE,NON_ACTIVE
    }
    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();
    @OneToOne(mappedBy = "userId")
    private UserRoom userRoom;
}
