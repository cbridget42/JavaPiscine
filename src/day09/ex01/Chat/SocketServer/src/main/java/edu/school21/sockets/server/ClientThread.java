package edu.school21.sockets.server;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UsersService;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.LocalDateTime;

public class ClientThread extends Thread {

    @Getter
    private final Socket clientSocket;
    private final UsersService usersService;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private Boolean runClient;

    public ClientThread(Socket clientSocket, UsersService usersService) {
        this.clientSocket = clientSocket;
        this.usersService = usersService;
    }

    @Override
    public void run() {

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            sendMessage("Hello from Server!\n");
            runClient = true;
            while (runClient) {
                sendMessage("enter a command\n");
                switch (bufferedReader.readLine()) {
                    case "signUp":
                        signUp();
                        break;
                    case "signIn":
                        signIn();
                        break;
                    case "Exit":
                        stopClient("You have left the chat.\n");
                        break;
                    default:
                        sendMessage("wrong command! try again!\n");
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void signUp() throws IOException {
        String name = requestData("Enter username:");
        String pass = requestData("Enter password:");
        try {
            usersService.signUp(new User(name, pass));
            sendMessage("Successful!\n");
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            sendMessage(e.getMessage() + "\n");
        }
    }

    private void signIn() throws IOException {
        String name = requestData("Enter username:");
        String pass = requestData("Enter password:");

        try {
            usersService.signIn(new User(name, pass));
            sendMessage("Successful!\n");
            messaging();
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            sendMessage("Incorrect name and password!\n");
        }
    }

    private void messaging() throws IOException {

        sendMessage("Start messaging\n");
        while (runClient) {
            String message = bufferedReader.readLine().trim();
            if ("Exit".equals(message)) {
                stopClient("You have left the chat.\n");
                break;
            } else {
                usersService.saveMessage(new Message(message, LocalDateTime.now()));
                broadcastMessage(message);
            }
        }
    }

    private void broadcastMessage(String message) throws IOException {
        for (ClientThread client : Server.getClients()) {
            if (ObjectUtils.notEqual(client.getClientSocket(), clientSocket)) {
                client.sendMessage(message);
            }
        }
    }

    private void stopClient(String message) throws IOException {
        sendMessage(message);
        Server.getClients().removeIf(client -> client.getClientSocket().equals(clientSocket));
        clientSocket.close();
        bufferedReader.close();
        bufferedWriter.close();

        this.runClient = false;
    }

    private String requestData(String request) throws IOException {
        sendMessage(request + "\n");
        return bufferedReader.readLine().trim();
    }

    private void sendMessage(String message) throws IOException {
        bufferedWriter.write(message);
        bufferedWriter.flush();
    }
}
