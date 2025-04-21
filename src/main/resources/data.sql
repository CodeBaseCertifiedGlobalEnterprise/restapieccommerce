INSERT INTO category (id, name) VALUES (1, 'Mobile');
INSERT INTO category (id, name) VALUES (2, 'Laptop');
INSERT INTO category (id, name) VALUES (3, 'Tablet');
INSERT INTO category (id, name) VALUES (4, 'Audio');
INSERT INTO category (id, name) VALUES (5, 'Wearable');
INSERT INTO category (id, name) VALUES (6, 'Smart Home');

INSERT INTO product (name, category_id, price, description, product_image_url, created_At)
VALUES ('iPhone 13', 1, 799.99, 'Latest iPhone model with A15 Bionic chip', 'http://localhost:8080/images/iphone13.jpg', '2025-04-14 10:00:00');

INSERT INTO product (name, category_id, price, description, product_image_url, created_At)
VALUES ('MacBook Pro 2023', 2, 1999.99, 'High-performance laptop with M2 Pro chip', 'http://localhost:8080/images/macbookpro2023.jpg', '2025-04-13 10:00:00');

INSERT INTO product (name, category_id, price, description, product_image_url, created_At)
VALUES ('iPad Air 5', 3, 599.99, 'Slim and powerful tablet with M1 chip', 'http://localhost:8080/images/ipadair5.jpg', '2025-04-12 10:00:00');

INSERT INTO product (name, category_id, price, description, product_image_url, created_At)
VALUES ('Apple Watch Series 8', 5, 399.99, 'Smartwatch with advanced health tracking', 'http://localhost:8080/images/applewatch8.jpg', '2025-04-11 10:00:00');

INSERT INTO product (name, category_id, price, description, product_image_url, created_At)
VALUES ('AirPods Pro 2', 4, 249.99, 'Noise-cancelling wireless earbuds', 'http://localhost:8080/images/airpodspro2.jpg', '2025-04-10 10:00:00');

INSERT INTO product (name, category_id, price, description, product_image_url, created_At)
VALUES ('Nest Thermostat', 6, 129.99, 'Smart thermostat with voice control', 'http://localhost:8080/images/nestthermostat.jpg', '2025-04-09 10:00:00');

INSERT INTO product (name, category_id, price, description, product_image_url, created_At)
VALUES ('iPhone 12 Mini', 1, 699.99, 'Compact iPhone with A14 chip', 'http://localhost:8080/images/iphone12mini.jpg', '2025-04-08 10:00:00');

INSERT INTO product (name, category_id, price, description, product_image_url, created_At)
VALUES ('MacBook Air M1', 2, 999.99, 'Lightweight laptop with Apple Silicon', 'http://localhost:8080/images/macbookairm1.jpg', '2025-04-07 10:00:00');

INSERT INTO product (name, category_id, price, description, product_image_url, created_At)
VALUES ('iPad Pro 11"', 3, 899.99, 'Tablet with Liquid Retina display and M2 chip', 'http://localhost:8080/images/ipadpro11.jpg', '2025-04-06 10:00:00');

INSERT INTO product (name, category_id, price, description, product_image_url, created_At)
VALUES ('Apple Watch SE', 5, 279.99, 'Affordable smartwatch with fitness tracking', 'http://localhost:8080/images/applewatchse.jpg', '2025-04-05 10:00:00');

-- ... truncated to keep the preview light ...

INSERT INTO product (name, category_id, price, description, product_image_url, created_At)
VALUES ('Amazon Echo Show 8', 6, 129.99, 'Smart display with Alexa', 'http://localhost:8080/images/echoshow8.jpg', '2025-03-16 10:00:00');


INSERT INTO store_users (user_name, email, password, role, date)
VALUES ('wisdommaliki', 'wisdommaliki@gmail.com', '$2a$10$lxn1nfV5E52cC1xeotA6/u.cHAGQM6kFthGNXT8pTFsYjWaFU3voS', 'USER', NOW());

--$2a$10$VrZ2Ez6GZMEgN3lmZbUv9eGIXcfJqQDFTHY/vRJvd12mYc6XKJ/Ci




