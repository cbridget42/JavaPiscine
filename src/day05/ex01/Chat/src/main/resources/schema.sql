CREATE SCHEMA IF NOT EXISTS chat1;

DROP TABLE IF EXISTS chat1.messages;
DROP TABLE IF EXISTS chat1.rooms;
DROP TABLE IF EXISTS chat1.users;

CREATE TABLE IF NOT EXISTS chat1.users (
    id SERIAL PRIMARY KEY,
    login text UNIQUE NOT NULL,
    password text NOT NULL
);

CREATE TABLE IF NOT EXISTS chat1.rooms (
    id SERIAL PRIMARY KEY,
    chat_name text UNIQUE NOT NULL,
    owner INT NOT NULL REFERENCES chat1.users(id)
);

CREATE TABLE IF NOT EXISTS chat1.messages (
    id SERIAL PRIMARY KEY,
    author INT NOT NULL REFERENCES chat1.users(id),
    room INT NOT NULL REFERENCES chat1.rooms(id),
    message text NOT NULL,
    send_time timestamp
);
