package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersService;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Component
public class Server {
    private final UsersService usersService;

    @Getter
    private static final List<ClientThread> clients = new ArrayList<>();

    public Server(UsersService usersService) {
        this.usersService = usersService;
    }

    public void start(int port) throws IOException {
        System.out.println("server is running!");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();

                ClientThread client = new ClientThread(clientSocket, usersService);
                clients.add(client);
                client.start();
            }
        }
    }

}
