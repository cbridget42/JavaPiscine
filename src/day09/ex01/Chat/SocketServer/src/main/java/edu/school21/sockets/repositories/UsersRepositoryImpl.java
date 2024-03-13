package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryImpl implements UsersRepository {

    private final JdbcTemplate jdbcTemplate;

    public UsersRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS server");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS server.user (\n" +
                "id SERIAL PRIMARY KEY,\n" +
                "name VARCHAR(50) NOT NULL UNIQUE,\n" +
                "password VARCHAR(256) NOT NULL);");
    }

    @Override
    public User findById(Long id) {
        String query = "SELECT * FROM server.user WHERE id = ?;";
        return jdbcTemplate.query(query, new Object[]{id}, new int[]{Types.INTEGER}, new BeanPropertyRowMapper<>(User.class))
                .stream()
                .findAny()
                .orElse(null);
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM server.user;";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void update(User entity) {
        String update = "UPDATE server.user SET name = ?, password = ? WHERE id = ?;";
        int res = jdbcTemplate.update(update, entity.getName(), entity.getPassword(), entity.getId());

        if (res == 0) {
            System.err.println("Entity hasn't been updated!");
        }
    }

    @Override
    public void save(User entity) {
        String update = "INSERT INTO server.user (name, password) VALUES (?, ?);";
        int res = jdbcTemplate.update(update, entity.getName(), entity.getPassword());

        if (res == 0) {
            System.err.println("Entity not created!");
        }
    }

    @Override
    public void delete(Long id) {
        String update = "DELETE FROM server.user WHERE id = ?;";
        int res = jdbcTemplate.update(update, id);

        if (res == 0) {
            System.err.println("row not deleted!");
        }
    }

    @Override
    public Optional<User> findByUserName(String name) {
        String query = "SELECT * FROM server.user WHERE name = ?";
        return jdbcTemplate.query(query, new Object[]{name}, new int[]{Types.VARCHAR}, new BeanPropertyRowMapper<>(User.class))
                .stream()
                .findAny();
    }
}
