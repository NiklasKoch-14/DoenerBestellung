CREATE TABLE IF NOT EXISTS roles (
     id int NOT NULL generated always as identity PRIMARY KEY,
     role varchar(50)
);

CREATE TABLE IF NOT EXISTS users (
    id int NOT NULL generated always as identity PRIMARY KEY,
    username varchar(50),
    password varchar(200),
    email varchar(50),
    enabled boolean
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS products (

     id int NOT NULL generated always as identity PRIMARY KEY,
     name varchar(50),
     price double precision
);