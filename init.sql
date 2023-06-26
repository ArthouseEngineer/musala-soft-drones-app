CREATE SCHEMA if not exists musala_drones;


CREATE TABLE IF NOT EXISTS musala_drones.users
(
    id       SERIAL PRIMARY KEY,
    username varchar(50),
    password varchar(100),
    role     varchar(50)
);

CREATE TABLE IF NOT EXISTS musala_drones.drones
(
    id               SERIAL PRIMARY KEY,
    serial_number    VARCHAR(100),
    model            VARCHAR(20)                 NOT NULL,
    current_workload NUMERIC(8, 2) DEFAULT 0.0   NOT NULL,
    weight_limit     NUMERIC(8, 2)               NOT NULL,
    battery_capacity INT                         NOT NULL,
    state            VARCHAR(10)                 NOT NULL,
    is_delivered     BOOLEAN       DEFAULT FALSE NOT NULL
);


CREATE TABLE IF NOT EXISTS musala_drones.medications
(
    id         SERIAL PRIMARY KEY,
    code       VARCHAR(20)   NOT NULL,
    name       VARCHAR(100)  NOT NULL,
    weight     NUMERIC(8, 2) NOT NULL,
    image_path VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS musala_drones.drone_medications
(
    load_id       BIGSERIAL PRIMARY KEY,
    drone_id      BIGINT NOT NULL,
    medication_id BIGINT NOT NULL,
    FOREIGN KEY (drone_id) REFERENCES musala_drones.drones (id),
    FOREIGN KEY (medication_id) REFERENCES musala_drones.medications (id)
);


INSERT INTO musala_drones.drones (serial_number, model, weight_limit, battery_capacity, state, is_delivered)
VALUES ('DRONE_01', 'LIGHTWEIGHT', 100, 50, 'IDLE', FALSE),
       ('DRONE_02', 'MIDDLEWEIGHT', 250, 55, 'IDLE', FALSE),
       ('DRONE_03', 'CRUISERWEIGHT', 350, 75, 'IDLE', FALSE),
       ('DRONE_04', 'CRUISERWEIGHT', 400, 85, 'IDLE', FALSE),
       ('DRONE_05', 'HEAVYWEIGHT', 500, 15, 'IDLE', FALSE),
       ('DRONE_06', 'LIGHTWEIGHT', 100, 25, 'IDLE', FALSE),
       ('DRONE_07', 'MIDDLEWEIGHT', 250, 30, 'IDLE', FALSE),
       ('DRONE_08', 'CRUISERWEIGHT', 350, 50, 'IDLE', FALSE),
       ('DRONE_09', 'CRUISERWEIGHT', 400, 90, 'IDLE', FALSE),
       ('DRONE_10', 'HEAVYWEIGHT', 500, 10, 'IDLE', FALSE);

INSERT INTO musala_drones.medications (code, name, weight, image_path)
VALUES ('PARACETAMOL', 'Paracetamol', 25.00, '/images/paracetamol.jpg'),
       ('IBUPROFEN', 'Ibuprofen', 20.00, '/images/ibuprofen.jpg'),
       ('ASPIRIN', 'Aspirin', 50.00, '/images/aspirin.jpg'),
       ('AMOXICILLIN', 'Amoxicillin', 65.00, '/images/amoxicillin.jpg'),
       ('LORATADINE', 'Loratadine', 100.00, NULL),
       ('CEPHALEXIN', 'Cephalexin', 50.00, '/images/cephalexin.jpg'),
       ('METFORMIN', 'Metformin', 111.11, NULL),
       ('PREDNISONE', 'Prednisone', 11.09, '/images/prednisone.jpg'),
       ('FLUCONAZOLE', 'Fluconazole', 12.07, '/images/fluconazole.jpg'),
       ('PANTOPRAZOLE', 'Pantoprazole', 14.06, NULL);


INSERT INTO drone_medications (drone_id, medication_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (2, 5),
       (3, 1),
       (3, 3),
       (3, 6),
       (3, 7),
       (4, 2),
       (4, 3),
       (4, 4),
       (4, 5),
       (4, 6),
       (5, 6),
       (5, 7),
       (5, 8);