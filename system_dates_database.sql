CREATE DATABASE system_dates_database;

use system_dates_database;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(100)  NOT NULL,
    last_name VARCHAR(100)  NOT NULL,
    email VARCHAR(150)  NOT NULL UNIQUE,
    gender VARCHAR(20) NOT NULL,
    date  DATE NOT NULL,
    password VARCHAR(255)  NOT NULL
);


