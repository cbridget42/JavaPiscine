package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;

@Component
public class MessageRepositoryImpl implements MessageRepository {

    private final JdbcTemplate jdbcTemplate;

    public MessageRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS server");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS server.message (\n" +
                "id SERIAL PRIMARY KEY,\n" +
                "text VARCHAR(1000) NOT NULL,\n" +
                "time timestamp default current_timestamp);");
    }

    @Override
    public Message findById(Long id) {
        String query = "SELECT * FROM server.message WHERE id = ?;";
        return jdbcTemplate.query(query, new Object[]{id}, new int[]{Types.INTEGER}, new BeanPropertyRowMapper<>(Message.class))
                .stream()
                .findAny()
                .orElse(null);
    }

    @Override
    public List<Message> findAll() {
        String query = "SELECT * FROM server.message;";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Message.class));
    }

    @Override
    public void update(Message entity) {
        String update = "UPDATE server.message SET text = ?, time = ? WHERE id = ?;";
        int res = jdbcTemplate.update(update, entity.getText(), entity.getTime(), entity.getId());

        if (res == 0) {
            System.err.println("Entity hasn't been updated!");
        }
    }

    @Override
    public void save(Message entity) {
        String update = "INSERT INTO server.message (text, time) VALUES (?, ?);";
        int res = jdbcTemplate.update(update, entity.getText(), entity.getTime());

        if (res == 0) {
            System.err.println("Entity not created!");
        }
    }

    @Override
    public void delete(Long id) {
        String update = "DELETE FROM server.message WHERE id = ?;";
        int res = jdbcTemplate.update(update, id);

        if (res == 0) {
            System.err.println("row not deleted!");
        }
    }
}
