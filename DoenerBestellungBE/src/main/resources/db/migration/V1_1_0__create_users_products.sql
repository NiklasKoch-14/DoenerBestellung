CREATE TABLE IF NOT EXISTS roles (
     id int NOT NULL generated always as identity PRIMARY KEY,
     role varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS orders (
          id int NOT NULL generated always as identity PRIMARY KEY,
          created_at timestamp default current_timestamp
);

CREATE TABLE IF NOT EXISTS users (
    id int NOT NULL generated always as identity PRIMARY KEY,
    username varchar(50) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    email varchar(50),
    enabled boolean,
    default_order_id int,
    paypal varchar(255),
    CONSTRAINT fk_default_order FOREIGN KEY (default_order_id) REFERENCES orders (id)
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

CREATE TABLE categories (
    id int NOT NULL generated always as identity PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS products (
     id int NOT NULL generated always as identity PRIMARY KEY,
     category_id int NOT NULL,
     name varchar(50) NOT NULL,
     description varchar(255),
     price double precision NOT NULL,
     CONSTRAINT fk_categories FOREIGN KEY (category_id) REFERENCES categories (id)
);


-- OrderItems (Zwischentabelle für N:M)
CREATE TABLE order_items (
     order_id INT NOT NULL,
     product_id INT NOT NULL,
     quantity INT NOT NULL DEFAULT 1,
     PRIMARY KEY (order_id, product_id),
     CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(id),
     CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE scheduled_orders (
    id int NOT NULL generated always as identity PRIMARY KEY,
    order_id int NOT NULL,
    order_name varchar(255),
    status varchar(50) default 'NEW',
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(id)
);