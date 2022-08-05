create table if not exists lazy_mark(
    id serial primary key,
    name varchar not null unique
);

create table if not exists lazy_model(
    id serial primary key,
    name varchar not null unique
);