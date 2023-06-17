package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class Chatroom {
    private Long id;
    private String name;
    private User owner;
    private List<Message> messages;

    public Chatroom(Long id, String name, User owner, List<Message> messages) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.messages = messages;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else {
            Chatroom tmp = (Chatroom)obj;
            return id.equals(tmp.id) && name.equals(tmp.name) && owner.equals(tmp.owner)
                    && messages.equals(tmp.messages);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner, messages);
    }

    @Override
    public String toString() {
        String user = (owner == null) ? null : owner.toString();
        String mess = (messages == null) ? null : messages.toString();
        return String.format("id=%d,name=\"%s\",creator=%s,messages=%s",
                id, name, user, mess);
    }
}
