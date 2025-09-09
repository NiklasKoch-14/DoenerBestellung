INSERT INTO roles (role) values ('ADMIN');
INSERT INTO roles (role) values ('CHEF');

INSERT INTO users (username, password, email, enabled, role_id)
    values ('tuser', 'tuser', 'tuser@email.com', true, 1);

INSERT INTO products (name, price) values ('Döner', 9.50);
