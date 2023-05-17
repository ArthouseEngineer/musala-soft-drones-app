CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    username varchar(50),
    password varchar(50),
    role     varchar(50),
);


CREATE TABLE IF NOT EXISTS DRONES
(
    serial_number    VARCHAR(100) PRIMARY KEY,
    model            VARCHAR(20) NOT NULL,
    weight_limit     INT         NOT NULL,
    battery_capacity INT         NOT NULL,
    state            VARCHAR(10) NOT NULL
);


CREATE TABLE IF NOT EXISTS medication
(
    code   VARCHAR(20) PRIMARY KEY,
    name   VARCHAR(100)  NOT NULL,
    weight NUMERIC(8, 2) NOT NULL
);


INSERT INTO USERS (username, password, role)
VALUES ('john_doe', 'password123', 'admin'),
       ('jane_doe', 'password456', 'user'),
       ('bob_smith', 'password789', 'admin'),
       ('alice_wonderland', 'password123', 'user');


INSERT INTO DRONES (serial_number, model, weight_limit, battery_capacity, state)
VALUES ('DRONE_001', 'LIGHTWEIGHT', 100, 15, 'IDLE');
INSERT INTO DRONES (serial_number, model, weight_limit, battery_capacity, state)
VALUES ('DRONE_002', 'MIDDLEWEIGHT', 250, 15, 'IDLE');
INSERT INTO DRONES (serial_number, model, weight_limit, battery_capacity, state)
VALUES ('DRONE_003', 'CRUISERWEIGHT', 350, 15, 'IDLE');
INSERT INTO DRONES (serial_number, model, weight_limit, battery_capacity, state)
VALUES ('DRONE_004', 'CRUISERWEIGHT', 400, 15, 'IDLE');
INSERT INTO DRONES (serial_number, model, weight_limit, battery_capacity, state)
VALUES ('DRONE_005', 'HEAVYWEIGHT', 500, 15, 'IDLE');

INSERT INTO MEDICATION (code, name, weight)
VALUES ('MEDICATION001', 'Melanotan', 80);
INSERT INTO MEDICATION (code, name, weight)
VALUES ('MEDICATION002', 'Clemastine', 50);
INSERT INTO MEDICATION (code, name, weight)
VALUES ('MEDICATION003', 'Ibuprofen', 110);

