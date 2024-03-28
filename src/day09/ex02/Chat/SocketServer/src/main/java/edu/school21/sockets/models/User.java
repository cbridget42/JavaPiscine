package edu.school21.sockets.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private Long id;
    private Long lastRoomId;
    private String name;
    private String password;

    public User(String name, String pass) {
        this.lastRoomId = -1L;
        this.name = name;
        this.password = pass;
    }

    public User() {}
}
