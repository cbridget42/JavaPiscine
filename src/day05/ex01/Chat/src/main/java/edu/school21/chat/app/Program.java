package edu.school21.chat.app;

import edu.school21.chat.repositories.MessagesRepository;
import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import java.util.Scanner;
import java.util.Optional;
import edu.school21.chat.models.Message;

public class Program {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USERNAME = "cbridget";
    private static final String DB_PASSWORD = "123";
    //private static final String

    public static void main(String[] args) {
        MessagesRepository mesRepository = new MessagesRepositoryJdbcImpl(getHikariDataSource());
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a message ID");
        Long id = sc.nextLong();
        Optional<Message> optMessage = mesRepository.findById(id);
        if (optMessage.isPresent()) {
            System.out.println(optMessage.get().toString());
        } else {
            System.out.println("no message");
        }
    }

    public static DataSource getHikariDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_URL);
        config.setUsername(DB_USERNAME);
        config.setPassword(DB_PASSWORD);
        return new HikariDataSource(config);
    }
}
