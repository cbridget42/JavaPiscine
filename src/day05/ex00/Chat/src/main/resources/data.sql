INSERT INTO chat.users (login, password) VALUES ('user_1', '123');
INSERT INTO chat.users (login, password) VALUES ('user_2', '1234');
INSERT INTO chat.users (login, password) VALUES ('user_3', '12345');
INSERT INTO chat.users (login, password) VALUES ('user_4', '123456');
INSERT INTO chat.users (login, password) VALUES ('user_5', 'admin');

INSERT INTO chat.rooms (chat_name, owner) VALUES ('room_1', 1);
INSERT INTO chat.rooms (chat_name, owner) VALUES ('room_2', 2);
INSERT INTO chat.rooms (chat_name, owner) VALUES ('room_3', 3);
INSERT INTO chat.rooms (chat_name, owner) VALUES ('room_4', 4);
INSERT INTO chat.rooms (chat_name, owner) VALUES ('room_5', 5);

INSERT INTO chat.messages (author, room, message, send_time)
    VALUES (1, 1, 'bla_bla', '2021-01-01 01:01:01');
INSERT INTO chat.messages (author, room, message, send_time)
    VALUES (2, 2, 'kek_kek', '2021-01-01 01:01:01');
INSERT INTO chat.messages (author, room, message, send_time)
    VALUES (3, 3, 'lol', '2021-01-01 01:01:01');
INSERT INTO chat.messages (author, room, message, send_time)
    VALUES (4, 4, 'bla_bla', '2021-01-01 01:01:01');
INSERT INTO chat.messages (author, room, message, send_time)
    VALUES (5, 5, 'lolul', '2021-01-01 01:01:01');