package edu.school21.sockets.server;

import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

@Component
//@RequiredArgsConstructor
public class Server {
    private final UsersService usersService;
    BufferedWriter bufferedWriter;

    public Server(UsersService usersService) {
        this.usersService = usersService;
    }

    public void start(int port) throws IOException {
        System.out.println("server is running!");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                sendMessage("Hello from Server!\n");
                while (true) {
                    if (ObjectUtils.notEqual(bufferedReader.readLine(), "signUp")) {
                        sendMessage("you need to sign up!\n");
                    } else {
                        break;
                    }
                }
                sendMessage("Enter username:\n");
                String name = bufferedReader.readLine().trim();
                sendMessage("Enter password:\n");
                String pass = bufferedReader.readLine().trim();
                try {
//                    ignoringExc(() -> usersService.signUp(new User(name, pass)));
                    usersService.signUp(new User(name, pass));
                    sendMessage("Successful!\n");
                } catch (RuntimeException e) {
                    System.err.println(e);
                    sendMessage("Internal server error!\n");
                }
                clientSocket.close();
                bufferedReader.close();
                bufferedWriter.close();
            }
        }
    }

    private void sendMessage(String message) throws IOException {
        bufferedWriter.write(message);
        bufferedWriter.flush();
    }

    private static void ignoringExc(RunnableExc r) throws Exception {
        try {
            r.run();
        } catch (RuntimeException ignore) { }
    }
}

@FunctionalInterface
interface RunnableExc {
    void run() throws Exception;
}
