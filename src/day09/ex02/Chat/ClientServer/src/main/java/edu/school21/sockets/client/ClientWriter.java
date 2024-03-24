package edu.school21.sockets.client;

import lombok.AllArgsConstructor;

import java.io.BufferedWriter;
import java.util.Scanner;

@AllArgsConstructor
public class ClientWriter extends Thread {

    private BufferedWriter writer;
    private Scanner scanner;

    @Override
    public void run() {
        try {
            while (true) {
                writer.write(scanner.nextLine() + '\n');
                writer.flush();
            }
        } catch (Exception e) {
            System.err.println(e);
            stopWriter(1);
        }
    }

    private void stopWriter(int status) {
        System.exit(status);
    }
}
