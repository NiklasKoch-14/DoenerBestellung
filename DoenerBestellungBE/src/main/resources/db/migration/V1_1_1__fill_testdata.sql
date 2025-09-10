INSERT INTO roles (role) values ('ADMIN');
INSERT INTO roles (role) values ('CHEF');

INSERT INTO users (username, password, email, enabled)
    values ('tuser', 'tuser', 'tuser@email.com', true);

INSERT INTO user_roles (user_id, role_id)
values (1,1);

INSERT INTO products (name, price) values ('Döner', 9.50);
