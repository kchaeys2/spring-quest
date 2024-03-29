package com.example.demo.domain.room.entity;

import com.example.demo.domain.BaseEntity;
import com.example.demo.domain.room.dto.response.RoomResponse;
import com.example.demo.domain.room.dto.response.RoomsResponse;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.userRoom.entity.UserRoom;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@Getter
public class Room extends BaseEntity {
    @Id @GeneratedValue
    @Column(nullable = false)
    private Integer id;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User host;
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    @Enumerated(EnumType.STRING)
    private RoomStatus status;
    @OneToMany(mappedBy = "roomId", cascade = CascadeType.ALL)
    private final List<UserRoom> joinUsers = new ArrayList<>();

    public Room(User user, RoomType roomType, String title) {
        this.host = user;
        this.roomType = roomType;
        this.title = title;
        this.status = RoomStatus.WAIT;
    }

    public void outUser(UserRoom userRoom) {
        joinUsers.remove(userRoom);
    }

    public RoomsResponse createRoomsResponse() {
        return new RoomsResponse(id, title, host.getId(), roomType, status);
    }

    public boolean checkJoinUserAble() {
        return roomType.getTotal() > joinUsers.size();
    }

    public RoomResponse createRoomResponse() {
        String formattedCreateAt = changeDateFormat(createdAt);
        String formattedUpdateAt = changeDateFormat(updatedAt);
        int hostId = host.getId();

        return new RoomResponse(id, title, hostId, roomType, status, formattedCreateAt, formattedUpdateAt);
    }

    public void setStatusFinish() {
        status = RoomStatus.FINISH;
    }

    public void setStatusProgress() {
        status = RoomStatus.PROGRESS;
    }
}
