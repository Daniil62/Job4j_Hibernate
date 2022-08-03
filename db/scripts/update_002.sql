create table if not exists mark(
    id serial primary key,
    name varchar not null unique
);

create table if not exists model(
    id serial primary key,
    name varchar not null unique
);