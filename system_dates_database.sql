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
    id_appointment  INT  NOT NULL UNIQUE, 
    id_user         INT  NOT NULL,
    id_business     INT  NOT NULL,
    rating          INT  NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment         TEXT,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_appointment) REFERENCES appointment(id_appointment),
    FOREIGN KEY (id_user)        REFERENCES users(id_user),
    FOREIGN KEY (id_business)    REFERENCES business(id_business)
);

-- registros

INSERT INTO users (first_name, last_name, email, gender, birth_date, password, role) VALUES
('Samuel', 'Frías',  'samuel@gmail.com',  'Hombre', '2004-03-15', '$2a$10$ZHfokperq5wzeC6Xe3ifze9SQhO.g6mthtsRA.1Vnsvt0c1liS2Te', 'ADMIN'),
('Isaac',  'Camacho', 'isaac@gmail.com', 'Hombre', '2003-07-22', '$2a$10$ZHfokperq5wzeC6Xe3ifze9SQhO.g6mthtsRA.1Vnsvt0c1liS2Te', 'USUARIO'),
('Luis', 'García', 'luis@gmail.com', 'Hombre', '1998-05-10', '$2a$10$ZHfokperq5wzeC6Xe3ifze9SQhO.g6mthtsRA.1Vnsvt0c1liS2Te', 'USUARIO'),
('Ana', 'López', 'ana@gmail.com', 'Mujer', '2000-09-18', '$2a$10$ZHfokperq5wzeC6Xe3ifze9SQhO.g6mthtsRA.1Vnsvt0c1liS2Te', 'USUARIO'),
('Jorge', 'Martínez', 'jorge@gmail.com', 'Hombre', '1995-11-22', '$2a$10$ZHfokperq5wzeC6Xe3ifze9SQhO.g6mthtsRA.1Vnsvt0c1liS2Te', 'USUARIO'),
('María', 'Hernández', 'maria@gmail.com', 'Mujer', '2001-04-30', '$2a$10$ZHfokperq5wzeC6Xe3ifze9SQhO.g6mthtsRA.1Vnsvt0c1liS2Te', 'USUARIO'),
('Pedro', 'Sánchez', 'pedro@gmail.com', 'Hombre', '1997-07-14', '$2a$10$ZHfokperq5wzeC6Xe3ifze9SQhO.g6mthtsRA.1Vnsvt0c1liS2Te', 'USUARIO'),
('Daniela', 'Torres', 'daniela@gmail.com', 'Mujer', '2002-03-08', '$2a$10$ZHfokperq5wzeC6Xe3ifze9SQhO.g6mthtsRA.1Vnsvt0c1liS2Te', 'USUARIO'),
('Ricardo', 'Castro', 'ricardo@gmail.com', 'Hombre', '1999-12-01', '$2a$10$ZHfokperq5wzeC6Xe3ifze9SQhO.g6mthtsRA.1Vnsvt0c1liS2Te', 'USUARIO'),
('Fernanda', 'Ruiz', 'fernanda@gmail.com', 'Mujer', '2001-06-25', '$2a$10$ZHfokperq5wzeC6Xe3ifze9SQhO.g6mthtsRA.1Vnsvt0c1liS2Te', 'USUARIO');

INSERT INTO business (name, address, phone, opening_time, close_time, qualification) VALUES
('Barbería El Estilo', 'Av. Reforma 245, La Paz, BCS', '6121234567', '09:00:00', '20:00:00', 4.5),
('Barbería Elegance', 'Centro, La Paz, BCS', '6121111111', '09:00:00', '19:00:00', 4.3),
('Barber Shop Elite', 'Zona Comercial, La Paz, BCS', '6122222222', '10:00:00', '20:00:00', 4.6),
('Barbería Urban Style', 'Col. Roma, La Paz, BCS', '6123333333', '08:00:00', '18:00:00', 4.1),
('The Gentlemen Barber', 'Av. Universidad, La Paz, BCS', '6124444444', '09:00:00', '21:00:00', 4.8),
('Barbería Premium', 'Centro Histórico, La Paz, BCS', '6125555555', '10:00:00', '20:00:00', 4.7),
('Barbería Golden Cut', 'Zona Norte, La Paz, BCS', '6126666666', '08:00:00', '19:00:00', 4.2),
('Barbería Black Style', 'Col. Indeco, La Paz, BCS', '6127777777', '09:00:00', '20:00:00', 4.4),
('Barbería Modern Men', 'Zona Sur, La Paz, BCS', '6128888888', '09:00:00', '21:00:00', 4.5),
('Barbería Kings', 'Malecón, La Paz, BCS', '6129999999', '10:00:00', '22:00:00', 4.9);

INSERT INTO employee (id_business, first_name, last_name, experience) VALUES
(1, 'Carlos',   'Mendoza',  5),
(1, 'Roberto',  'Llanes',   3),
(3, 'Miguel',   'Soto',     7),
(4, 'Fernando', 'Ramos',    2),
(2,'Andrés','Pérez',4),
(1,'José','Gómez',6),
(7,'Raúl','Vargas',8),
(1,'Mario','Navarro',5),
(9,'Eduardo','Silva',2),
(10,'Hugo','Morales',7);

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

INSERT INTO appointment
(id_user,id_business,id_service,id_employee,appointment_date,start_time,end_time,status,notes)
VALUES
(1,1,1,1,'2026-06-01','09:00:00','09:30:00','completada','Cliente frecuente'),
(2,1,2,2,'2026-06-01','10:00:00','10:50:00','completada',''),
(3,1,1,1,'2026-06-02','11:00:00','11:30:00','confirmada',''),
(4,1,3,2,'2026-06-02','12:00:00','12:20:00','completada',''),
(5,1,1,1,'2026-06-03','13:00:00','13:30:00','completada',''),
(6,1,2,2,'2026-06-03','14:00:00','14:50:00','confirmada',''),
(7,1,4,1,'2026-06-04','15:00:00','16:00:00','pendiente',''),
(8,1,3,2,'2026-06-04','16:00:00','16:20:00','completada',''),
(9,1,4,1,'2026-06-05','17:00:00','18:00:00','cancelada','No asistió'),
(10,1,5,2,'2026-06-05','18:00:00','19:30:00','completada','');


-- consultas

-- Verificar credenciales de usuario
SELECT id_user, first_name, last_name, email, gender, birth_date, role FROM users WHERE email = ?;

-- Obtener servicios del negocio
SELECT id_service, name, description, duration_min, price FROM service WHERE id_business = 1 ;

-- Verificar conflictos de horario
SELECT COUNT(*) FROM appointment WHERE id_employee = ? AND appointment_date = ? AND status != 'cancelada' AND ((start_time <= ? AND end_time > ?) OR (start_time < ? AND end_time >= ?)); 

-- Insertar nueva cita 
INSERT INTO appointment (id_user, id_business, id_service, id_employee, appointment_date, start_time, end_time, status) VALUES (?, 1, ?, ?, ?, ?, ?, 'pendiente'); 

-- Citas del día con JOIN 
SELECT u.first_name, u.last_name, s.name AS servicio, e.first_name AS empleado, a.start_time, a.end_time, a.status FROM appointment a JOIN users u ON a.id_user = u.id_user JOIN service s ON a.id_service = s.id_service JOIN employee e ON a.id_employee = e.id_employee WHERE a.appointment_date = CURDATE() ORDER BY a.start_time; 

-- Ingresos por mes agrupados 
SELECT YEAR(a.appointment_date) AS anio, MONTH(a.appointment_date) AS mes, COUNT(*) AS total_citas, SUM(s.price) AS ingresos_totales FROM appointment a JOIN service s ON a.id_service = s.id_service WHERE a.status = 'completada' GROUP BY YEAR(a.appointment_date), MONTH(a.appointment_date) ORDER BY anio DESC, mes DESC; 

-- Empleados con más citas (ranking) 
SELECT e.first_name, e.last_name, COUNT(a.id_appointment) AS total_citas, AVG(r.rating) AS calificacion_promedio FROM employee e LEFT JOIN appointment a ON e.id_employee = a.id_employee AND a.status = 'completada' LEFT JOIN review r ON a.id_appointment = r.id_appointment GROUP BY e.id_employee ORDER BY total_citas DESC; 

-- Servicios más solicitados 
SELECT s.name, COUNT(a.id_appointment) AS veces_solicitado, SUM(s.price) AS ingresos FROM service s LEFT JOIN appointment a ON s.id_service = a.id_service GROUP BY s.id_service ORDER BY veces_solicitado DESC; 

-- Subconsulta: usuarios con más citas que el promedio 
SELECT u.first_name, u.last_name, u.email, COUNT(a.id_appointment) AS total FROM users u JOIN appointment a ON u.id_user = a.id_user GROUP BY u.id_user HAVING COUNT(a.id_appointment) > (SELECT AVG(cnt) FROM (SELECT COUNT(*) cnt FROM appointment GROUP BY id_user) t);


-- Vistas

-- Vista 1: Resumen de citas con información completa 
CREATE VIEW v_appointments_detail AS SELECT a.id_appointment, CONCAT(u.first_name, ' ', u.last_name) AS cliente, s.name AS servicio, s.price, CONCAT(e.first_name, ' ', e.last_name) AS empleado, a.appointment_date AS fecha, a.start_time AS inicio, a.end_time AS fin, a.status AS estado, a.notes AS notas FROM appointment a JOIN users u ON a.id_user = u.id_user JOIN service s ON a.id_service = s.id_service LEFT JOIN employee e ON a.id_employee = e.id_employee; 

-- Vista 2: Reporte de reseñas del negocio 
CREATE VIEW v_business_reviews AS SELECT r.id_review, CONCAT(u.first_name, ' ', u.last_name) AS cliente, r.rating, r.comment, r.created_at, s.name AS servicio_calificado FROM review r JOIN users u ON r.id_user = u.id_user JOIN appointment a ON r.id_appointment = a.id_appointment JOIN service s ON a.id_service = s.id_service WHERE r.id_business = 1 ORDER BY r.created_at DESC; 

-- Vista 3: Disponibilidad de empleados 
CREATE VIEW v_employee_schedule AS SELECT e.id_employee, CONCAT(e.first_name, ' ', e.last_name) AS empleado, a.appointment_date AS fecha, a.start_time AS inicio, a.end_time AS fin, s.name AS servicio, a.status FROM employee e LEFT JOIN appointment a ON e.id_employee = a.id_employee AND a.status != 'cancelada' LEFT JOIN service s ON a.id_service = s.id_service ORDER BY e.id_employee, a.appointment_date, a.start_time; 

-- Vista 4: Estadísticas de servicios 
CREATE VIEW v_service_stats AS SELECT s.id_service, s.name AS servicio, s.price AS precio, s.duration_min AS duracion_min, COUNT(a.id_appointment) AS total_citas, COALESCE(SUM(s.price), 0) AS ingresos_generados FROM service s LEFT JOIN appointment a ON s.id_service = a.id_service AND a.status = 'completada' GROUP BY s.id_service; 



