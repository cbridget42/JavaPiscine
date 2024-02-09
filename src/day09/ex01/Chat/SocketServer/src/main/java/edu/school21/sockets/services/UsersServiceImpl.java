package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(User user) {
        if (usersRepository.findByUserName(user.getName()).isPresent()) {
            throw new RuntimeException("User: " + user.getName() + " already exist!");
        } else {
            user.setPass(passwordEncoder.encode(user.getPass()));
            usersRepository.save(user);
        }
    }
}
