package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class User {
    private Long id;
    private String login;
    private String password;
    private List<Chatroom> createdRooms;
    private List<Chatroom> socializesRooms;

    public User(Long id, String login, String password, List<Chatroom> createdRooms,
         List<Chatroom> socializesRooms) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.createdRooms = createdRooms;
        this.socializesRooms = socializesRooms;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else {
            User tmp = (User)obj;
            return id.equals(tmp.id) && login.equals(tmp.login) && password.equals(tmp.password)
                    && createdRooms.equals(tmp.createdRooms)
                    && socializesRooms.equals(tmp.socializesRooms);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, createdRooms, socializesRooms);
    }

    @Override
    public String toString() {
        String cRooms = (createdRooms == null) ? null : createdRooms.toString();
        String sRooms = (socializesRooms == null) ? null : socializesRooms.toString();
        return String.format("id=%d,login=\"%s\",password=\"%s\",createdRooms=%s,rooms=%s",
                id, login, password, cRooms, sRooms);
    }

    public Long getId() {return id;}
}
