create table if not exists author(
    id serial primary key,
    name varchar not null unique
);

create table if not exists book(
    id serial primary key,
    name varchar not null unique
);