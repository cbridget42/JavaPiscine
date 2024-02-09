package edu.school21.sockets.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    Long id;
    String name;
    String pass;

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public User() {}
}
