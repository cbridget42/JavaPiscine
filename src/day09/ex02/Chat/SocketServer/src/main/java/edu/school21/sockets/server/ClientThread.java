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
import java.util.Objects;
import java.util.stream.Collectors;

public class ClientThread extends Thread {

    private static final String EXIT = "Exit";
    private static final String WRONG_COMMAND = "wrong command! try again!\n";
    private static final String LEFT_CHAT = "You have left the chat.\n";
    @Getter
    private final Socket clientSocket;
    private final UsersService usersService;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private Boolean runClient;
    @Getter
    private String clientName;
    @Getter
    private Long roomId;

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
                switch (selectCommand(Arrays.asList("signIn", "signUp", EXIT))) {
                    case 1:
                        signIn();
                        break;
                    case 2:
                        signUp();
                        break;
                    case 3:
                        stopClient(LEFT_CHAT);
                        break;
                    default:
                        sendMessage(WRONG_COMMAND);
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void roomLoop() throws IOException {
        while (runClient) {
            switch (selectCommand(Arrays.asList("Create room", "Choose room", EXIT))) {
                case 1:
                    createRoom();
                    break;
                case 2:
                    chooseRoom();
                    break;
                case 3:
                    stopClient(LEFT_CHAT);
                    break;
                default:
                    sendMessage(WRONG_COMMAND);
                    break;
            }
        }
    }

    private void createRoom() throws IOException {
        String name = requestData("Enter room name:");
        try {
            usersService.createRoom(new Room(name, clientName));
            sendMessage("Successful!\n");
            this.roomId = usersService.findRoom(name)
                    .orElseThrow(() -> new RuntimeException("Failed to create room."))
                    .getId();
            messaging();
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            sendMessage(e.getMessage() + "\n");
        }
    }

    private void chooseRoom() throws IOException {
        List<Room> rooms = usersService.findAllRooms();
        List<String> commands = rooms.stream()
                .map(Room::getName)
                .collect(Collectors.toList());
        commands.add(EXIT);
        while (runClient) {
            int val = selectCommand(commands) - 1;
            if (val == commands.size() - 1) {
                stopClient(LEFT_CHAT);
            } else if (0 <= val && val < rooms.size()) {
                this.roomId = rooms.get(val).getId();
                messaging();
            } else {
                sendMessage(WRONG_COMMAND);
            }
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
            printLastMessages();
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
            printLastMessages();
            roomLoop();
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            sendMessage("Incorrect name and password!\n");
        }
    }

    private void messaging() throws IOException {
        sendMessage("Start messaging\n");
        while (runClient) {
            String message = bufferedReader.readLine().trim();
            if (EXIT.equals(message)) {
                stopClient(LEFT_CHAT);
            } else {
                usersService.saveMessage(new Message(message, LocalDateTime.now(), this.roomId));
                broadcastMessage(message);
            }
        }
    }

    private void broadcastMessage(String message) throws IOException {
        for (ClientThread client : Server.getClients()) {
            if (ObjectUtils.notEqual(client.getClientName(), this.clientName)
                    && Objects.equals(client.getRoomId(), this.roomId)) {
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

    private void printLastMessages() {
        usersService.findUserByName(this.clientName)
                .filter(user -> ObjectUtils.notEqual(user.getLastRoomId(), -1L))
                .ifPresent(user -> {
                    try {
                        sendMessage("Last messages:\n");
                        List<String> messages = usersService.findMessagesByRoomId(user.getLastRoomId())
                                .stream()
                                .map(Message::getText)
                                .collect(Collectors.toList());
                        for (int i = (messages.size() > 29) ? messages.size() - 30 : 0; i < messages.size(); i++) {
                            sendMessage(messages.get(i) + '\n');
                        }
                    } catch (IOException e) {
                        System.err.println("ignored exception:\n" + e);
                    }
                });
    }
}
