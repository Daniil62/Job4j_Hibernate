CREATE TABLE IF NOT EXISTS orders(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    description VARCHAR(50),
    created timestamp
);