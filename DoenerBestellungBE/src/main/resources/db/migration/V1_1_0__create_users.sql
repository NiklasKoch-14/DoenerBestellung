CREATE TABLE IF NOT EXISTS roles (
     id int NOT NULL generated always as identity PRIMARY KEY,
     role varchar(50)
);

CREATE TABLE IF NOT EXISTS users (

    id int NOT NULL generated always as identity PRIMARY KEY,
    username varchar(50),
    password varchar(200),
    email varchar(50),
    enabled boolean,
    role_id INT,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS products (

     id int NOT NULL generated always as identity PRIMARY KEY,
     name varchar(50),
     price double precision
);