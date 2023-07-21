package school21.spring.service.repositories;

import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl<T> implements UsersRepository<T> {
    private static final String FIND_ALL = "SELECT * FROM chat.user";
    private static final String FIND_BY_ID = "SELECT * FROM chat.user WHERE id=?";
    private static final String FIND_BY_EMAIL = "SELECT * FROM chat.user WHERE email=?";
    private static final String SAVE = "INSERT INTO chat.user (id, email) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE chat.user SET email=? WHERE id=?";
    private static final String DELETE = "DELETE FROM chat.user WHERE id=?";
    private final DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<T> findById(Long id) {
        Optional<T> result = Optional.empty();

        try (Connection dbConnection = dataSource.getConnection();
             PreparedStatement pStatement = dbConnection.prepareStatement(FIND_BY_ID)) {
            pStatement.setLong(1, id);
            ResultSet resSet = pStatement.executeQuery();
            if (!resSet.next()) {
                return result;
            }
            @SuppressWarnings("unchecked")
            T instance = (T) new User(resSet.getLong(1),
                    resSet.getString(2));
            result = Optional.of(instance);
        } catch (Exception e) {
            printError(e.getMessage());
        }
        return result;
    }

    @Override
    public List<T> findAll() {
        List<T> result = new ArrayList<>();

        try (Connection dbConnection = dataSource.getConnection();
             PreparedStatement statement = dbConnection.prepareStatement(FIND_ALL)) {
            ResultSet resSet = statement.executeQuery();
            while (resSet.next()) {
                @SuppressWarnings("unchecked")
                T instance = (T) new User(resSet.getLong(1),
                        resSet.getString(2));
                result.add(instance);
            }
        } catch (Exception e) {
            printError(e.getMessage());
        }
        return result;
    }

    @Override
    public void save(T entity) {
        User user = (User) entity;
        try (Connection dbConnection = dataSource.getConnection();
         PreparedStatement statement = dbConnection.prepareStatement(SAVE)) {
            statement.setLong(1, user.getId());
            statement.setString(2, user.getEmail());
            statement.execute();
        } catch (Exception e) {
            printError(e.getMessage());
        }
    }

    @Override
    public void update(T entity) {
        User user = (User) entity;
        try (Connection dbConnection = dataSource.getConnection();
             PreparedStatement statement = dbConnection.prepareStatement(UPDATE)) {
            statement.setLong(2, user.getId());
            statement.setString(1, user.getEmail());
            statement.execute();
        } catch (Exception e) {
            printError(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection dbConnection = dataSource.getConnection();
             PreparedStatement statement = dbConnection.prepareStatement(DELETE)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (Exception e) {
            printError(e.getMessage());
        }
    }

    @Override
    public Optional<T> findByEmail(String email) {
        Optional<T> result = Optional.empty();

        try (Connection dbConnection = dataSource.getConnection();
             PreparedStatement pStatement = dbConnection.prepareStatement(FIND_BY_EMAIL)) {
            pStatement.setString(1, email);
            ResultSet resSet = pStatement.executeQuery();
            if (!resSet.next()) {
                return result;
            }
            @SuppressWarnings("unchecked")
            T instance = (T) new User(resSet.getLong(1),
                    resSet.getString(2));
            result = Optional.of(instance);
        } catch (Exception e) {
            printError(e.getMessage());
        }
        return result;
    }

    private void printError(String err) {
        System.err.println(err);
    }
}
