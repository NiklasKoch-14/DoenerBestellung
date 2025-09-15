INSERT INTO roles (role)
values ('ADMIN');
INSERT INTO roles (role)
values ('CHEF');

INSERT INTO users (username, password, email, enabled, paypal)
values ('tim', '$2a$10$F5pYA1hRgwzF.hjzgPdpqeSZzmsxGslsaZPEbCEN4IH80S9GDOJGO', 'tim@email.com', true, 'www.paypal.me/tim');

INSERT INTO user_roles (user_id, role_id)
values (1, 1);

INSERT INTO categories (name)
VALUES ('Döner'),
       ('Dürüm'),
       ('Pide'),
       ('Lahmacun'),
       ('Pizza'),
       ('Calzone');

INSERT INTO products (category_id, name, description, price)
VALUES (1, 'Döner Sandwich', 'Mit Fleisch und Salat', 8.00),
       (1, 'Gemüse Döner Sandwich', 'Mit Gemüse und Sauce', 8.00),
       (1, 'Döner Sandwich Jumbo', 'Extra groß', 9.00),
       (1, 'Gemüse Döner Sandwich Jumbo', 'Extra groß mit Gemüse', 9.00),
       (1, 'Döner Box', 'Fleisch mit Pommes und Sauce', 8.00),
       (1, 'Gemüse Döner Box', 'Gemüse mit Pommes und Sauce', 8.00),
       (1, 'Döner Teller', 'Mit Salat und Sauce', 12.00),
       (1, 'Gemüse Döner Teller', 'Mit Salat und Sauce', 11.00),
       (1, 'Jumbo Döner Teller', 'Extra groß', 13.00),
       (1, 'Gemüse Jumbo Döner Teller', 'Extra groß', 12.00);

-- Dürüm
INSERT INTO products (category_id, name, description, price)
VALUES (2, 'Dürüm Döner', 'Mit Fleisch, Salat und Sauce', 8.50),
       (2, 'Gemüse Dürüm', 'Mit Gemüse, Salat und Sauce', 8.00),
       (2, 'Jumbo Dürüm', 'Extra groß', 9.50),
       (2, 'Gemüse Jumbo Dürüm', 'Extra groß', 9.00),
       (2, 'Falafel Dürüm', 'Mit Salat und Sauce', 7.00);

-- Pide (Schiffchen)
INSERT INTO products (category_id, name, description, price)
VALUES (3, 'Pide Spinat und Käse', NULL, 9.50),
       (3, 'Pide Hackfleisch', NULL, 10.00),
       (3, 'Pide Sucuk', 'Mit Knoblauchwurst', 10.00),
       (3, 'Pide Käse', NULL, 9.00),
       (3, 'Pide Döner', NULL, 11.00),
       (3, 'Pide Hähnchen Döner', NULL, 11.00),
       (3, 'Pide Gemischt', 'Mit Sucuk', 11.50);

-- Lahmacun
INSERT INTO products (category_id, name, description, price)
VALUES (4, 'Lahmacun', 'Türkische Pizza mit Hackfleisch', 6.00),
       (4, 'Lahmacun mit Käse', NULL, 6.50),
       (4, 'Lahmacun mit Salat', NULL, 7.00),
       (4, 'Lahmacun mit Dönerfleisch', NULL, 9.00),
       (4, 'Lahmacun mit Hähnchenfleisch', NULL, 9.00),
       (4, 'Jumbo Lahmacun', NULL, 7.50),
       (4, 'Jumbo Lahmacun mit Käse', NULL, 8.00),
       (4, 'Jumbo Lahmacun mit Salat', NULL, 8.50),
       (4, 'Jumbo Lahmacun mit Dönerfleisch', NULL, 10.00),
       (4, 'Jumbo Lahmacun mit Hähnchenfleisch', NULL, 10.00);

-- Pizza
INSERT INTO products (category_id, name, description, price)
VALUES (5, 'Pizza Margherita', 'Tomaten und Käse', 7.50),
       (5, 'Pizza Prosciutto', 'Mit Schinken', 8.50),
       (5, 'Pizza Salami', NULL, 8.50),
       (5, 'Pizza Funghi', 'Mit Champignons', 8.50),
       (5, 'Pizza Tonno', 'Mit Thunfisch und Zwiebeln', 9.00),
       (5, 'Pizza Napoli', 'Mit Sardellen', 9.00),
       (5, 'Pizza Vegetaria', 'Gemüse', 9.00),
       (5, 'Pizza Hackfleisch', NULL, 9.50),
       (5, 'Pizza Döner', NULL, 10.00),
       (5, 'Pizza Hähnchen Döner', NULL, 10.00),
       (5, 'Pizza Gemischt (mit Sucuk)', NULL, 10.50);

-- Pizza Calzone
INSERT INTO products (category_id, name, description, price)
VALUES (6, 'Calzone', 'Gefüllt mit Schinken, Salami und Käse', 11.50),
       (6, 'Calzone Spezial', 'Gefüllt mit Dönerfleisch, Sucuk und Käse', 12.00),
       (6, 'Calzone Hähnchen Döner', 'Gefüllt mit Hähnchenfleisch und Käse', 12.00),
       (6, 'Calzone Gemischt (mit Sucuk)', NULL, 12.50);