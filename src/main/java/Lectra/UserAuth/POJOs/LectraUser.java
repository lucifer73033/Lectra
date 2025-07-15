package Lectra.UserAuth.POJOs;

import Lectra.Room.POJOs.Room;

import java.util.ArrayList;
import java.util.List;

public class LectraUser {
    private String id;
    private String name;
    private List<String> roles;
    private List<Room> createdRooms;
    private List<Room> joinedRooms;

    public LectraUser(String id, String name, List<String> roles) {
        this.id = id;
        this.name = name;
        this.roles = roles;
        this.createdRooms=new ArrayList<>();
        this.joinedRooms=new ArrayList<>();
    }

    public LectraUser(String id, String name, List<String> roles, List<Room> createdRooms, List<Room> joinedRooms) {
        this.id = id;
        this.name = name;
        this.roles = roles;
        this.createdRooms = createdRooms;
        this.joinedRooms = joinedRooms;
    }

    public LectraUser() {
    }

    public List<Room> getCreatedRooms() {
        return new ArrayList<>(this.createdRooms);
    }

    public void setCreatedRooms(List<Room> createdRooms) {
        this.createdRooms = createdRooms;
    }

    public List<Room> getJoinedRooms() {
        return new ArrayList<>(joinedRooms);
    }

    public void setJoinedRooms(List<Room> joinedRooms) {
        this.joinedRooms = joinedRooms;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getRoles() {
        return new ArrayList<>(roles);
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
