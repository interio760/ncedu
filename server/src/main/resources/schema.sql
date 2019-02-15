CREATE TABLE usr
(
  user_id            BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_name          VARCHAR(64)  not null,
  user_password      VARCHAR(128) not null,
  user_email         VARCHAR(128) not null,
  user_refresh_token VARCHAR(128),
  user_is_activated BOOLEAN
);

CREATE TABLE role
(
  role_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
  role_name VARCHAR(64) not null
);

CREATE TABLE assigned_role
(
  user_id BIGINT REFERENCES usr (user_id),
  role_id BIGINT REFERENCES role (role_id)
);

CREATE TABLE planet(
                     planet_id BIGSERIAL PRIMARY KEY,
                     planet_name VARCHAR(64) not null
);

CREATE TABLE spaceport(
                        spaceport_id BIGSERIAL PRIMARY KEY,
                        spaceport_name VARCHAR(64) not null,
                        creation_date DATE NOT NULL DEFAULT CURRENT_DATE,
                        role_id BIGSERIAL REFERENCES planet (planet_id)
);

CREATE TABLE vehicle(
  vehicle_id BIGSERIAL PRIMARY KEY,
  owner_id BIGSERIAL REFERENCES usr (user_id),
  vehicle_name VARCHAR(64) not null,
  vehicle_seats INT
);

CREATE TABLE trip(
  trip_id BIGSERIAL PRIMARY KEY,
  vehicle_id BIGSERIAL REFERENCES vehicle (vehicle_id),
  trip_status INT,
  creation_date DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE ticket_class(
                   ticket_id BIGSERIAL PRIMARY KEY,
                   trip_id BIGSERIAL REFERENCES trip (trip_id),
                   ticket_price INT
)