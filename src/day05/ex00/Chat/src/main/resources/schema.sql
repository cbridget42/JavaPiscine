create schema if not exists chat;

create table if not exists chat.users (
    id serial primary key,
    login text unique not null
);