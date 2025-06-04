CREATE DATABASE library;

USE library;

CREATE TABLE books (
    isbn VARCHAR(255) PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255),
    stock INT,
    borrow_count INT
);