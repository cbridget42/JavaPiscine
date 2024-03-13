package edu.school21.sockets.app;

import edu.school21.sockets.client.Client;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1 || !args[0].matches("--serverPort=\\d{4}")) {
            throw new IllegalArgumentException("must be argument --serverPort=****");
        }
        Client client = new Client(Integer.parseInt(args[0].substring(13)));
        try {
            client.start();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
