DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS DRONES;
DROP TABLE IF EXISTS MEDICATION;


CREATE TABLE USERS
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username varchar(50),
    password varchar(50),
    roles    VARCHAR(25) ARRAY
);


CREATE TABLE DRONES
(
    serial_number    VARCHAR(100) PRIMARY KEY,
    model            VARCHAR(20) NOT NULL,
    weight_limit     INT         NOT NULL,
    battery_capacity INT         NOT NULL,
    state            VARCHAR(10) NOT NULL
);


CREATE TABLE MEDICATION
(
    code   VARCHAR(20) PRIMARY KEY,
    name   VARCHAR(50) NOT NULL,
    weight FLOAT       NOT NULL
);
