package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.Room;
import edu.school21.sockets.models.User;

import java.util.List;
import java.util.Optional;

public interface UsersService {

    void signUp(User user);

    void signIn(User user);

    void saveMessage(Message message);

    void createRoom(Room room);

    List<Room> findAllRooms();

    Optional<Room> findRoom(String name);

    Optional<User> findUserByName(String name);

    List<Message> findMessagesByRoomId(Long roomId);
}
