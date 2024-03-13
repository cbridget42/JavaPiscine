package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;

public interface UsersService {

    void signUp(User user);

    void signIn(User user);

    void saveMessage(Message message);
}
