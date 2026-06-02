CREATE DATABASE system_dates_database;
USE system_dates_database;

CREATE TABLE users (
    id_user      INT PRIMARY KEY AUTO_INCREMENT,
    first_name   VARCHAR(100)  NOT NULL,
    last_name    VARCHAR(100)  NOT NULL,
    email        VARCHAR(150)  NOT NULL UNIQUE,
    gender       VARCHAR(20)   NOT NULL,
    birth_date    DATE          NOT NULL,
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


INSERT INTO users (first_name, last_name, email, gender, birth_date, password, role) VALUES
('Samuel', 'Frías',  'samuel@gmail.com',  'Hombre', '2004-03-15', '$2a$10$ZHfokperq5wzeC6Xe3ifze9SQhO.g6mthtsRA.1Vnsvt0c1liS2Te', 'USUARIO'),
('Isaac',  'Camacho', 'isaac@gmail.com', 'Hombre', '2003-07-22', '$2a$10$ZHfokperq5wzeC6Xe3ifze9SQhO.g6mthtsRA.1Vnsvt0c1liS2Te', 'ADMIN');

INSERT INTO business (name, address, phone, opening_time, close_time, qualification) VALUES
('Barbería El Estilo', 'Av. Reforma 245, La Paz, BCS', '6121234567', '09:00:00', '20:00:00', 4.5);

INSERT INTO employee (id_business, first_name, last_name, experience) VALUES
(1, 'Carlos',   'Mendoza',  5),
(1, 'Roberto',  'Llanes',   3),
(1, 'Miguel',   'Soto',     7),
(1, 'Fernando', 'Ramos',    2);

INSERT INTO service (id_business, name, description, duration_min, price) VALUES
(1, 'Corte de cabello',  'Corte clásico o moderno a tu elección',   30, 120.00),
(1, 'Corte + barba',     'Corte de cabello más arreglo de barba',   50, 200.00),
(1, 'Arreglo de barba',  'Perfilado y arreglo con navaja',          20,  90.00),
(1, 'Tinte',             'Aplicación de tinte en cabello completo', 60, 350.00),
(1, 'Keratina',          'Tratamiento de alisado con keratina',     90, 500.00);

INSERT INTO employee_service (id_employee, id_service) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), 
(2, 1), (2, 2), (2, 3), 
(3, 1), (3, 2), (3, 3), (3, 4), (3, 5), 
(4, 1), (4, 3);
