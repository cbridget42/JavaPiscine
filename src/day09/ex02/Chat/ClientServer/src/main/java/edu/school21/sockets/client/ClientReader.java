package edu.school21.sockets.client;

import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;

@AllArgsConstructor
public class ClientReader extends Thread {

    private BufferedReader reader;

    @Override
    public void run() {
        try {
            while (true) {
                String str = reader.readLine();
                System.out.println(str);
                if ("You have left the chat.".equals(str) || "Internal server error!".equals(str)) {
                    stopReader(0);
                }
            }
        } catch (Exception e) {
            System.err.println(e);
            stopReader(1);
        }
    }

    private void stopReader(int status) {
        System.exit(status);
    }
}
