package edu.school21.chat.repositories;

import edu.school21.chat.models.*;
import javax.sql.DataSource;
import java.sql.*;
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
    public Optional<Message> findById(Long id) {
        Optional<Message> result = Optional.empty();;

        try (Connection dbConnection = dataSource.getConnection()) {
            PreparedStatement pStatement = dbConnection.prepareStatement(FIND_MESSAGE_TEMPLATE);
            pStatement.setLong(1, id);
            ResultSet resSet = pStatement.executeQuery();
            if (!resSet.next()) {
                return result;
            }
            User user = findUser(resSet.getLong(2), dbConnection);
            Chatroom room = findChatroom(resSet.getLong(3), dbConnection);
            result = Optional.of(new Message(id, user, room, resSet.getString(4),
                    resSet.getTimestamp(5).toLocalDateTime()));
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
