package edu.school21.chat.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private Long id;
    private User author;
    private Chatroom room;
    private String text;
    private LocalDateTime date;

    public Message(Long id, User author, Chatroom room, String text,
                   LocalDateTime date) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else {
            Message tmp = (Message)obj;
            return id.equals(tmp.id) && author.equals(tmp.author) && room.equals(tmp.room)
                    && tmp.equals(tmp.text) && date.equals(tmp.date);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, room, text, date);
    }

    @Override
    public String toString() {
        String user = (author == null) ? null : author.toString();
        String sRoom = (room == null) ? null : room.toString();
        return String.format("Message : {%nid=%d,%nauthor={%s},%nroom={%s},%ntext=\"%s\",%ndateTime=%s%n}",
                id, user, sRoom, text, date.toString());
    }
}