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
    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
    private final List<Room> rooms = new ArrayList<>();
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;
    private Integer fakerId;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToOne(mappedBy = "userId")
    private UserRoom userRoom;

    public User(Integer id, String username, String email) {
        this.fakerId = id;
        this.name = username;
        this.email = email;
        this.status = setStatus(id);
    }

    private Status setStatus(Integer id) {
        if (id <= 30) {
            return Status.ACTIVE;
        }
        if (id <= 60) {
            return Status.WAIT;
        }
        return Status.NON_ACTIVE;
    }

    public UserResponse createUserResponse() {
        return new UserResponse(id, fakerId, name, email, status.name(), createAt.toString(), updateAt.toString());
    }

    public Integer getId() {
        return id;
    }

    enum Status {
        WAIT, ACTIVE, NON_ACTIVE
    }
}
