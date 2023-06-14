package edu.school21.chat.models;

import java.util.List;

public class Chatroom {
    private Integer id;
    private String name;
    private User owner;
    private List<Message> messages;
}
