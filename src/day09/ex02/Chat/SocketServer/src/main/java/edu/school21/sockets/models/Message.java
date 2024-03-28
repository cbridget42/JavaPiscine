package edu.school21.sockets.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Message {

    private Long id;
    private String text;
    private Long roomId;
    private LocalDateTime time;

    public Message(String text, LocalDateTime dateTime, Long roomId) {
        this.text = text;
        this.time = dateTime;
        this.roomId = roomId;
    }

    public Message() {}
}
