package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UsersServiceImplTest {

    UsersService usersService;
    @Mock
    UsersRepositoryImpl mockUsersRepository;

    @Test
    void signUpTest() {
        this.usersService = new UsersServiceImpl(mockUsersRepository, null, null);
        User user = new User("42", "21");
        Mockito.when(mockUsersRepository.findByUserName(Mockito.any()))
                .thenReturn(Optional.of(user));
        Assertions.assertThrows(RuntimeException.class, () -> usersService.signUp(user));
        Mockito.verify(mockUsersRepository, Mockito.times(1)).findByUserName(Mockito.any());
    }
}
