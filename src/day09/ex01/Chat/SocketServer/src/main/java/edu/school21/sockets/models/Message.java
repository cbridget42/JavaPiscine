package edu.school21.sockets.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Message {

    Long id;
    String text;
    LocalDateTime time;

    public Message(String text, LocalDateTime dateTime) {
        this.text = text;
        this.time = dateTime;
    }

    public Message() {}
}
