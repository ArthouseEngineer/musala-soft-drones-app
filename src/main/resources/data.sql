INSERT INTO USERS (username, password, roles)
VALUES ('john_doe', 'password123', ARRAY['admin', 'user']),
       ('jane_doe', 'password456', ARRAY['user']),
       ('bob_smith', 'password789', ARRAY['admin']),
       ('alice_wonderland', 'password123', ARRAY['user', 'manager']);


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