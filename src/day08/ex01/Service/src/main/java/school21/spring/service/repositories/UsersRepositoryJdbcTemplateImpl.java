package school21.spring.service.repositories;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl<T> implements UsersRepository<T> {
    private static final String FIND_ALL = "SELECT * FROM chat.user";
    private static final String FIND_BY_ID = "SELECT * FROM chat.user WHERE id=?";
    private static final String FIND_BY_EMAIL = "SELECT * FROM chat.user WHERE email=?";
    private static final String SAVE = "INSERT INTO chat.user (id, email) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE chat.user SET email=? WHERE id=?";
    private static final String DELETE = "DELETE FROM chat.user WHERE id=?";
    JdbcTemplate jdbcTemplate;

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<T> findById(Long id) {
        @SuppressWarnings("unchecked")
        T user = (T) jdbcTemplate.query(FIND_BY_ID, new Object[]{id}, new int[] {Types.BIGINT},
                        new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
        return (user == null)? Optional.empty() : Optional.of(user);
    }

    @Override
    public List<T> findAll() {
        @SuppressWarnings("unchecked")
        List<T> res = (List<T>) (List<?>) jdbcTemplate.query(FIND_ALL,
                new BeanPropertyRowMapper<>(User.class));
        return res;
    }

    @Override
    public void save(T entity) {
        User user = (User) entity;
        jdbcTemplate.update(SAVE, user.getId(), user.getEmail());
    }

    @Override
    public void update(T entity) {
        User user = (User) entity;
        jdbcTemplate.update(UPDATE, user.getEmail(), user.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public Optional<T> findByEmail(String email) {
        @SuppressWarnings("unchecked")
        T user = (T) jdbcTemplate.query(FIND_BY_EMAIL, new Object[]{email}, new int[] {Types.VARCHAR},
                        new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
        return (user == null)? Optional.empty() : Optional.of(user);
    }
}
