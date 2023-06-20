package edu.school21.chat.repositories;

import edu.school21.chat.models.*;
import edu.school21.chat.exceptions.*;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private static final String FIND_MESSAGE_TEMPLATE = "SELECT * FROM chat.messages WHERE id=?";
    private static final String FIND_USER_TEMPLATE = "SELECT * FROM chat.users WHERE id=?";
    private static final String FIND_CHATROOM_TEMPLATE = "SELECT * FROM chat.users WHERE id=?";
    private DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void update(Message message) {
        String queryTemplate = "UPDATE chat.messages SET author=?, room=?, message=?, send_time=? " +
                "WHERE id=?;";
        try (Connection dbConnection = dataSource.getConnection();
             PreparedStatement statement = dbConnection.prepareStatement(queryTemplate)) {
            statement.setLong(1, message.getAuthor().getId());
            statement.setLong(2, message.getRoom().getId());
            statement.setString(3, message.getText());
            if (message.getDate() == null) {
                statement.setTimestamp(4, null);
            } else {
                statement.setTimestamp(4, Timestamp.valueOf(message.getDate()));
            }
            statement.setLong(5, message.getId());
            statement.execute();
        } catch (Exception e) {
            throw new NotSavedSubEntityException("message has not been updated");
        }
    }

    @Override
    public void save(Message message) {
        String date = (message.getDate() == null) ? "'null'" : "'" + Timestamp.valueOf(message.getDate()) + "'";
        String query = "INSERT INTO chat.messages (author, room, message, send_time) VALUES (" +
                message.getAuthor().getId() + ", " + message.getRoom().getId() + ", '" + message.getText() +
                "', " + date + ") RETURNING id";

        try (Connection dbConnection = dataSource.getConnection();
             Statement statement = dbConnection.createStatement()) {
            ResultSet resSet = statement.executeQuery(query);
            if (resSet.next()) {
                message.setId(resSet.getLong(1));
            } else {
                throw new NotSavedSubEntityException("database error");
            }
        } catch (NotSavedSubEntityException e) {
            throw e;
        } catch (Exception e) {
            throw new NotSavedSubEntityException("message wasn't created");
        }
    }

    @Override
    public Optional<Message> findById(Long id) {
        Optional<Message> result = Optional.empty();;

        try (Connection dbConnection = dataSource.getConnection();
            PreparedStatement pStatement = dbConnection.prepareStatement(FIND_MESSAGE_TEMPLATE)) {
            pStatement.setLong(1, id);
            ResultSet resSet = pStatement.executeQuery();
            if (!resSet.next()) {
                return result;
            }
            User user = findUser(resSet.getLong(2), dbConnection);
            Chatroom room = findChatroom(resSet.getLong(3), dbConnection);
            LocalDateTime dateTime = (resSet.getTimestamp(5) == null) ? null :
                    resSet.getTimestamp(5).toLocalDateTime();
            result = Optional.of(new Message(id, user, room, resSet.getString(4),
                    dateTime));
        } catch (Exception e) {
            printError(e.getMessage());
        }
        return result;
    }

    private Chatroom findChatroom(Long id, Connection dbConnection) {
        Chatroom res = null;
        try {
            PreparedStatement pStatement = dbConnection.prepareStatement(FIND_CHATROOM_TEMPLATE);
            pStatement.setLong(1, id);
            ResultSet resSet = pStatement.executeQuery();
            if (!resSet.next()) {
                return res;
            }
            res = new Chatroom(id, resSet.getString(2), null, null);
        } catch (Exception e) {
            printError(e.getMessage());
        }
        return res;
    }

    private User findUser(Long id, Connection dbConnection) {
        User res = null;
        try {
            PreparedStatement pStatement = dbConnection.prepareStatement(FIND_USER_TEMPLATE);
            pStatement.setLong(1, id);
            ResultSet resSet = pStatement.executeQuery();
            if (!resSet.next()) {
                return res;
            }
            res = new User(id, resSet.getString(2), resSet.getString(3), null, null);
        } catch (Exception e) {
            printError(e.getMessage());
        }
        return res;
    }

    private void printError(String err) {
        System.err.println(err);
        System.exit(-1);
    }
}
