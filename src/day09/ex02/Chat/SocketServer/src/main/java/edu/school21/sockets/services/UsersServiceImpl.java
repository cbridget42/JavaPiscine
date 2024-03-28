package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.Room;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessageRepository;
import edu.school21.sockets.repositories.RoomRepository;
import edu.school21.sockets.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(User user) {
        if (usersRepository.findByUserName(user.getName()).isPresent()) {
            throw new RuntimeException("User: " + user.getName() + " already exist!");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            usersRepository.save(user);
        }
    }

    @Override
    public void signIn(User user) {
        Optional<User> userFromDb = usersRepository.findByUserName(user.getName());

        if (BooleanUtils.isFalse(userFromDb.isPresent()) || BooleanUtils.isFalse(passwordEncoder.matches(user.getPassword(), userFromDb.get().getPassword()))) {
            throw new RuntimeException("Wrong password and username!");
        }
    }

    @Override
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    @Override
    public void createRoom(Room room) {
        if (roomRepository.findByRoomName(room.getName()).isPresent()) {
            throw new RuntimeException("Room: " + room.getName() + " already exist!");
        } else {
            roomRepository.save(room);
        }
    }

    @Override
    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Optional<Room> findRoom(String name) {
        return roomRepository.findByRoomName(name);
    }

    @Override
    public Optional<User> findUserByName(String name) {
        return usersRepository.findByUserName(name);
    }

    @Override
    public List<Message> findMessagesByRoomId(Long roomId) {
        return messageRepository.findByRoomId(roomId);
    }
}
