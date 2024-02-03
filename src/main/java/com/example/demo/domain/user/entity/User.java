package com.example.demo.domain.user.entity;


import com.example.demo.domain.BaseEntity;
import com.example.demo.domain.room.entity.Room;
import com.example.demo.domain.user.dto.response.UserResponse;
import com.example.demo.domain.userRoom.entity.UserRoom;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@NoArgsConstructor
public class User extends BaseEntity {
    @Id @GeneratedValue
    @Column(nullable = false)
    private Integer id;
    private Integer fakerId;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
    private final List<Room> rooms = new ArrayList<>();
    @OneToOne(mappedBy = "userId", cascade = CascadeType.ALL)
    private UserRoom userRoom;

    public User(Integer id, String username, String email) {
        this.fakerId = id;
        this.name = username;
        this.email = email;
        this.status = setStatus(id);
    }

    private UserStatus setStatus(Integer id) {
        if (id <= 30) {
            return UserStatus.ACTIVE;
        }
        if (id <= 60) {
            return UserStatus.WAIT;
        }
        return UserStatus.NON_ACTIVE;
    }

    public UserResponse createUserResponse() {
        String formattedCreateAt = changeDateFormat(createdAt);
        String formattedUpdateAt = changeDateFormat(updatedAt);

        return new UserResponse(id, fakerId, name, email, status, formattedCreateAt, formattedUpdateAt);
    }

    public Integer getId() {
        return id;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void outRoom() {
        userRoom = null;
    }

    public UserRoom getUserRoom() {
        return userRoom;
    }
}
