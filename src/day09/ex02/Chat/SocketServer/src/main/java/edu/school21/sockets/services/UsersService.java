package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.Room;
import edu.school21.sockets.models.User;

import java.util.List;

public interface UsersService {

    void signUp(User user);

    void signIn(User user);

    void saveMessage(Message message);

    void createRoom(Room room);

    List<Room> findAllRooms();
}
