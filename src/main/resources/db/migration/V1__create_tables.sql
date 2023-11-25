CREATE TABLE author (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE publisher (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE book_detail (
    id SERIAL PRIMARY KEY,
    page_count INT,
    language VARCHAR(50),
    available_online BOOLEAN,
    publication_date DATE
);

CREATE TABLE tag (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE book_detail_tag (
    book_detail_id SERIAL REFERENCES book_detail(id),
    tag_id SERIAL REFERENCES tag(id),
    PRIMARY KEY (book_detail_id, tag_id)
);

CREATE TABLE book (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author_id BIGINT REFERENCES author(id),
    publisher_id BIGINT REFERENCES publisher(id),
    book_detail_id BIGINT REFERENCES book_detail(id)
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
