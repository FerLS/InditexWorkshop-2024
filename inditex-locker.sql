CREATE DATABASE inditex_locker;

USE inditex_locker;

CREATE TABLE productos(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    stock INT NOT NULL
);

CREATE TABLE clientes(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre varchar(255) NOT NULL,
    direccionX INT,
    direccionY INT
);

CREATE TABLE lockers(
    id INT AUTO_INCREMENT PRIMARY KEY,
    direccionX INT,
    direccionY INT
);

CREATE TABLE obstaculos(
    direccionX INT,
    direccionY INT,
    primary key(direccionX, direccionY)
);

CREATE TABLE pedidos(
    id INT AUTO_INCREMENT PRIMARY KEY,
    producto_Id INT,
    cliente_id INT,
    locker_id INT,
    FOREIGN KEY (producto_id) REFERENCES productos(id),
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    FOREIGN KEY (locker_id) REFERENCES lockers(id)
);

INSERT INTO productos(nombre, stock) VALUES 
("Camiseta", 10),
("Pantalon", 18),
("Clips", 200),
("Bolso", 3);

INSERT INTO clientes(nombre, direccionX, direccionY) VALUES 
("Reese Johnson", 14, 37),
("Morgan Rodriguez", 46, 44),
("Morgan Brown", 31, 23),
("Alex Brown", 10, 29),
("Jamie Martinez", 43, 29),
("Casey Smith", 43, 31),
("Alex Brown", 42, 41),
("Jordan Williams", 18, 6),
("Riley Garcia", 9, 20),
("Avery Davis", 4, 0),
("Morgan Johnson", 31, 21),
("Avery Brown", 25, 37),
("Casey Davis", 15, 36),
("Reese Jones", 11, 25),
("Alex Garcia", 0, 12),
("Taylor Williams", 34, 21),
("Riley Williams", 2, 9),
("Sam Williams", 38, 25),
("Taylor Martinez", 48, 48),
("Avery Rodriguez", 40, 32),
("Jamie Martinez", 41, 27),
("Alex Davis", 45, 14),
("Avery Williams", 6, 49),
("Alex Smith", 46, 21),
("Morgan Jones", 24, 12),
("Reese Miller", 20, 37),
("Jamie Johnson", 4, 6),
("Casey Garcia", 29, 20),
("Jamie Johnson", 34, 30),
("Morgan Rodriguez", 25, 26),
("Taylor Jones", 1, 26),
("Jordan Brown", 6, 23),
("Reese Garcia", 36, 38),
("Jamie Williams", 23, 40),
("Riley Williams", 18, 8),
("Riley Rodriguez", 40, 42),
("Riley Williams", 18, 36),
("Alex Miller", 43, 28),
("Morgan Rodriguez", 17, 34),
("Taylor Williams", 4, 12),
("Sam Smith", 38, 8),
("Avery Smith", 33, 35),
("Jordan Williams", 34, 27),
("Reese Johnson", 30, 15),
("Jamie Miller", 15, 23),
("Riley Miller", 17, 39),
("Riley Smith", 38, 6),
("Reese Garcia", 11, 17),
("Jamie Brown", 46, 24),
("Jordan Williams", 26, 3);

INSERT INTO lockers(direccionX, direccionY) VALUES 
(4, 44),
(18, 46),
(24, 6),
(13, 26),
(25, 7),
(23, 23),
(2, 17),
(46, 43),
(14, 30),
(33, 32),
(15, 33),
(29, 34),
(13, 16),
(15, 13),
(23, 22),
(10, 13),
(25, 34),
(33, 45),
(10, 12),
(17, 11),
(20, 25),
(16, 1),
(6, 35),
(14, 40),
(12, 36),
(14, 42),
(27, 9),
(30, 45),
(30, 19),
(26, 48);

INSERT INTO obstaculos(direccionX, direccionY) VALUES
(21, 36),
(26, 17),
(35, 46),
(6, 50),
(17, 31),
(17, 4),
(33, 34),
(37, 29),
(38, 2),
(45, 41),
(15, 41),
(18, 5),
(12, 18),
(29, 13),
(40, 7),
(31, 7),
(31, 6),
(3, 38),
(8, 31),
(8, 20),
(32, 23),
(1, 34),
(25, 15),
(35, 49),
(28, 38),
(35, 45),
(28, 25),
(32, 25),
(35, 6),
(12, 28);
