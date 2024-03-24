package edu.school21.sockets.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Room {
    private Long id;
    private String name;
    private String owner;

    public Room(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

    public Room() {}
}
