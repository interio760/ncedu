DROP TABLE users_roles;
DROP TABLE roles;
DROP TABLE users;

DROP SEQUENCE roles_pk_seq;
DROP SEQUENCE users_pk_seq;

CREATE TABLE users (
  id INTEGER,
  username VARCHAR(45),
  password VARCHAR(1024)
);

ALTER TABLE users
ADD CONSTRAINT users_pk
PRIMARY KEY(id);

--------------------------------

CREATE TABLE roles (
  id INTEGER,
  role VARCHAR(45) 
);

ALTER TABLE roles
ADD CONSTRAINT roles_pk
PRIMARY KEY(id);

--------------------------------

CREATE TABLE users_roles (
  user_id INTEGER,
  role_id INTEGER
 );

ALTER TABLE users_roles 
ADD CONSTRAINT users_roles_pk
PRIMARY KEY (user_id, role_id);

ALTER TABLE users_roles 
ADD CONSTRAINT users_roles_user_fk
FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE users_roles 
ADD CONSTRAINT users_roles_role_fk
FOREIGN KEY (role_id) REFERENCES roles(id);

--------------------------------

CREATE SEQUENCE roles_pk_seq
MINVALUE 1
START WITH 1
INCREMENT BY 1
NOCYCLE;

CREATE SEQUENCE users_pk_seq
MINVALUE 1
START WITH 1
INCREMENT BY 1
NOCYCLE;

INSERT INTO roles(id, role) VALUES (roles_pk_seq.NEXTVAL, 'ROLE_ADMIN');
INSERT INTO roles(id, role) VALUES (roles_pk_seq.NEXTVAL, 'ROLE_USER');

COMMIT WORK;