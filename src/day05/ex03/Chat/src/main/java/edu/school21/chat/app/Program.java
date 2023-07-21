package edu.school21.chat.app;

import edu.school21.chat.repositories.*;
import edu.school21.chat.models.*;
import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import edu.school21.chat.models.Message;
public class Program {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USERNAME = "cbridget";
    private static final String DB_PASSWORD = "123";

    public static void main(String[] args) {
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(getHikariDataSource());
        Optional<Message> messageOptional = messagesRepository.findById(4l);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setText("Bye");
            message.setDate(null);
            messagesRepository.update(message);
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
