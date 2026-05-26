CREATE DATABASE system_dates_database;
USE system_dates_database;

DROP TABLE users;

SELECT * FROM users;

CREATE TABLE users (
    id_user      INT PRIMARY KEY AUTO_INCREMENT,
    first_name   VARCHAR(100)  NOT NULL,
    last_name    VARCHAR(100)  NOT NULL,
    email        VARCHAR(150)  NOT NULL UNIQUE,
    gender       VARCHAR(20)   NOT NULL,
    date    DATE          NOT NULL,
    password     VARCHAR(255)  NOT NULL,
    role ENUM('ADMIN', 'USUARIO'),
    created_at   TIMESTAMP     DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE business (
    id_business   INT PRIMARY KEY AUTO_INCREMENT,
    name          VARCHAR(100) NOT NULL,
    address       VARCHAR(150) NOT NULL,
    phone         VARCHAR(20),
    opening_time  TIME         NOT NULL,
    close_time    TIME         NOT NULL,
    qualification DECIMAL(2,1) DEFAULT 0.0
);

CREATE TABLE employee (
    id_employee  INT PRIMARY KEY AUTO_INCREMENT,
    id_business  INT          NOT NULL,
    first_name   VARCHAR(100) NOT NULL,
    last_name    VARCHAR(100) NOT NULL,
    experience   INT          DEFAULT 0,

    FOREIGN KEY (id_business) REFERENCES business(id_business)
);

CREATE TABLE service (
    id_service   INT PRIMARY KEY AUTO_INCREMENT,
    id_business  INT           NOT NULL,
    name         VARCHAR(100)  NOT NULL,
    description  TEXT,
    duration_min INT           NOT NULL,
    price        DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (id_business) REFERENCES business(id_business)
);

CREATE TABLE employee_service (
    id_employee  INT NOT NULL,
    id_service   INT NOT NULL,

    PRIMARY KEY (id_employee, id_service),
    FOREIGN KEY (id_employee) REFERENCES employee(id_employee),
    FOREIGN KEY (id_service)  REFERENCES service(id_service)
);

CREATE TABLE appointment (
    id_appointment  INT PRIMARY KEY AUTO_INCREMENT,
    id_user         INT       NOT NULL,
    id_business     INT       NOT NULL,
    id_service      INT       NOT NULL,
    id_employee     INT,
    appointment_date DATE     NOT NULL,
    start_time      TIME      NOT NULL,
    end_time        TIME      NOT NULL,
    status          ENUM('pendiente', 'confirmada', 'cancelada', 'completada') DEFAULT 'pendiente',
    notes           TEXT,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_user)     REFERENCES users(id_user),
    FOREIGN KEY (id_business) REFERENCES business(id_business),
    FOREIGN KEY (id_service)  REFERENCES service(id_service),
    FOREIGN KEY (id_employee) REFERENCES employee(id_employee)
);

CREATE TABLE review (
    id_review       INT PRIMARY KEY AUTO_INCREMENT,
    id_appointment  INT  NOT NULL UNIQUE,  -- one review per appointment
    id_user         INT  NOT NULL,
    id_business     INT  NOT NULL,
    rating          INT  NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment         TEXT,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_appointment) REFERENCES appointment(id_appointment),
    FOREIGN KEY (id_user)        REFERENCES users(id_user),
    FOREIGN KEY (id_business)    REFERENCES business(id_business)
);