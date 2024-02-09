package edu.school21.sockets.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private int port;

    public Client(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        try (Socket socket = new Socket("127.0.0.1", port)) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Scanner scanner = new Scanner(System.in);

            while (true) {
                String str = reader.readLine();
                System.out.println(str);
                if (str.equals("Successful!") || str.equals("Internal server error!")) {
                    break;
                } else {
                    writer.write(scanner.nextLine() + "\n");
                    writer.flush();
                }

            }
            reader.close();
            writer.close();
            scanner.close();
        }
    }
}
