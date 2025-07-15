package Lectra.Room.POJOs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Room {
    private String id;
    private String roomName;
    private String admin;
    private List<String> users;
    private Date createdAt;

    public Room(String id, String roomName, String admin, List<String> users, Date createdAt) {
        this.id = id;
        this.roomName = roomName;
        this.admin = admin;
        this.users = users;
        this.createdAt = createdAt;
    }

    public Room() {
    }

    public String getUuid() {
        return this.id;
    }

    public void setUuid(String uuid) {
        this.id = uuid;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public List<String> getUsers() {
        return new ArrayList<>(this.users);
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
