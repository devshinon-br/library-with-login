CREATE TABLE author (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE publisher (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE book (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author_id BIGINT REFERENCES author(id),
    publisher_id BIGINT REFERENCES publisher(id)
);

CREATE TABLE book_cover (
    id SERIAL PRIMARY KEY,
    cover_image_url VARCHAR(255),
    book_id BIGINT UNIQUE REFERENCES book(id)
);

CREATE TABLE genre (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE book_genre (
    book_id SERIAL REFERENCES book(id),
    genre_id SERIAL REFERENCES genre(id),
    PRIMARY KEY (book_id, genre_id)
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) REFERENCES users(username),
    authority VARCHAR(50) NOT NULL
);
