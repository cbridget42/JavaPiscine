package edu.school21.sockets.server;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.Room;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ClientThread extends Thread {

    @Getter
    private final Socket clientSocket;
    private final UsersService usersService;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private Boolean runClient;
    private String clientName;

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
                switch (selectCommand(Arrays.asList("signIn", "signUp", "Exit"))) {
                    case 1:
                        signIn();
//                        roomLoop();
                        break;
                    case 2:
                        signUp();
//                        roomLoop();
                        break;
                    case 3:
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

    private void roomLoop() throws IOException {
        while (runClient) {
            switch (selectCommand(Arrays.asList("Create room", "Choose room", "Exit"))) {
                case 1:
                    createRoom();
                    break;
                case 2:
//                    chooseRoom();
                    break;
                case 3:
                    stopClient("You have left the chat.\n");
                    break;
                default:
                    sendMessage("wrong command! try again!\n");
                    break;
            }
        }
    }

    private void createRoom() throws IOException {
        String name = requestData("Enter room name:");
        try {
            usersService.createRoom(new Room(name, clientName));
            sendMessage("Successful!\n");
            messaging();
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            sendMessage(e.getMessage() + "\n");
        }
    }

    private void chooseRoom() throws IOException {
        List<Room> rooms = usersService.findAllRooms();
        while (runClient) {
            int val = selectCommand(rooms.stream()
                    .map(Room::getName)
                    .collect(Collectors.toList()));
            //TODO
        }
    }

    private Integer selectCommand(List<String> commands) throws IOException {
        for (String command : commands) {
            sendMessage(command + '\n');
        }
        try {
            return Integer.parseInt(bufferedReader.readLine().trim());
        } catch (Exception e) {
            return -1;
        }
    }

    private void signUp() throws IOException {
        this.clientName = requestData("Enter username:");
        String pass = requestData("Enter password:");
        try {
            usersService.signUp(new User(this.clientName, pass));
            sendMessage("Successful!\n");
            roomLoop();
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            sendMessage(e.getMessage() + "\n");
        }
    }

    private void signIn() throws IOException {
        this.clientName = requestData("Enter username:");
        String pass = requestData("Enter password:");

        try {
            usersService.signIn(new User(this.clientName, pass));
            sendMessage("Successful!\n");
            roomLoop();
//            messaging();
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            sendMessage("Incorrect name and password!\n");
        }
    }

    private void messaging() throws IOException {//TODO fix this for new requirements
        sendMessage("Start messaging\n");
        while (runClient) {
            String message = bufferedReader.readLine().trim();
            if ("Exit".equals(message)) {
                stopClient("You have left the chat.\n");
            } else {
                usersService.saveMessage(new Message(message, LocalDateTime.now()));
                broadcastMessage(message);
            }
        }
    }

    private void broadcastMessage(String message) throws IOException {
        for (ClientThread client : Server.getClients()) {
            if (ObjectUtils.notEqual(client.getClientSocket(), clientSocket)) {
                client.sendMessage(message + '\n');
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
