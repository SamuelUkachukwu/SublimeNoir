DROP TABLE IF EXISTS order_item;

DROP TABLE IF EXISTS orders;

DROP TABLE IF EXISTS product;

DROP TABLE IF EXISTS users;

CREATE TABLE
    users (
              id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
              username VARCHAR(255),
              email VARCHAR(255) UNIQUE,
              first_name VARCHAR(255),
              last_name VARCHAR(255)
);

CREATE TABLE
    product (
                product_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255),
                brand VARCHAR(255),
                price DOUBLE,
                sizeML INT,
                quantity INT
);

CREATE TABLE
    orders (
               order_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
               order_date DATE,
               status VARCHAR(50),
               user_id BIGINT NOT NULL
);

CREATE TABLE
    order_item (
                   id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                   quantity INT,
                   price_at_purchase DOUBLE,
                   order_id BIGINT,
                   product_id BIGINT
);

ALTER TABLE orders ADD CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE order_item ADD CONSTRAINT fk_item_order FOREIGN KEY (order_id) REFERENCES orders (order_id);

ALTER TABLE order_item ADD CONSTRAINT fk_item_product FOREIGN KEY (product_id) REFERENCES product (product_id);