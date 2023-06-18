CREATE SCHEMA IF NOT EXISTS chat;

DROP TABLE IF EXISTS chat.messages;
DROP TABLE IF EXISTS chat.rooms;
DROP TABLE IF EXISTS chat.users;

CREATE TABLE IF NOT EXISTS chat.users (
    id SERIAL PRIMARY KEY,
    login text UNIQUE NOT NULL,
    password text NOT NULL
);

CREATE TABLE IF NOT EXISTS chat.rooms (
    id SERIAL PRIMARY KEY,
    chat_name text UNIQUE NOT NULL,
    owner INT NOT NULL REFERENCES chat.users(id)
);

CREATE TABLE IF NOT EXISTS chat.messages (
    id SERIAL PRIMARY KEY,
    author INT NOT NULL REFERENCES chat.users(id),
    room INT NOT NULL REFERENCES chat.rooms(id),
    message text UNIQUE NOT NULL,
    send_time timestamp
);
