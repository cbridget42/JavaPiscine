package edu.school21.sockets.app;

import edu.school21.sockets.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1 || !args[0].matches("--port=\\d{4}")) {
            throw new IllegalArgumentException("must be argument --port=****");
        }

        try {
            int port = Integer.parseInt(args[0].substring(7));
            ApplicationContext context = new AnnotationConfigApplicationContext("edu.school21.sockets");
            Server server = context.getBean(Server.class);
            server.start(port);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
