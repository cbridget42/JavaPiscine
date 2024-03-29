package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessageRepository;
import edu.school21.sockets.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final MessageRepository messageRepository;
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
}
