package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Room;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Component
public class RoomRepositoryImpl implements RoomRepository {

    private final JdbcTemplate jdbcTemplate;

    public RoomRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS server");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS server.room (\n" +
                "id SERIAL PRIMARY KEY,\n" +
                "name VARCHAR(50) NOT NULL UNIQUE,\n" +
                "owner VARCHAR(50) NOT NULL);");
    }

    @Override
    public Room findById(Long id) {
        String query = "SELECT * FROM server.room WHERE id = ?;";
        return jdbcTemplate.query(query, new Object[]{id}, new int[]{Types.INTEGER}, new BeanPropertyRowMapper<>(Room.class))
                .stream()
                .findAny()
                .orElse(null);
    }

    @Override
    public List<Room> findAll() {
        String query = "SELECT * FROM server.room;";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Room.class));
    }

    @Override
    public void update(Room entity) {
        String update = "UPDATE server.room SET name = ?, owner = ? WHERE id = ?;";
        int res = jdbcTemplate.update(update, entity.getName(), entity.getOwner(), entity.getId());

        if (res == 0) {
            System.err.println("Entity hasn't been updated!");
        }
    }

    @Override
    public void save(Room entity) {
        String update = "INSERT INTO server.room (name, owner) VALUES (?, ?);";
        int res = jdbcTemplate.update(update, entity.getName(), entity.getOwner());

        if (res == 0) {
            System.err.println("Entity not created!");
        }
    }

    @Override
    public void delete(Long id) {
        String update = "DELETE FROM server.room WHERE id = ?;";
        int res = jdbcTemplate.update(update, id);

        if (res == 0) {
            System.err.println("row not deleted!");
        }
    }

    @Override
    public Optional<Room> findByRoomName(String name) {
        String query = "SELECT * FROM server.room WHERE name = ?";
        return jdbcTemplate.query(query, new Object[]{name}, new int[]{Types.VARCHAR}, new BeanPropertyRowMapper<>(Room.class))
                .stream()
                .findAny();
    }
}
