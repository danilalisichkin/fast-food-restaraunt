\c fast_food_restaurant_database;

CREATE TABLE users
(
    phone      VARCHAR(15) PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    email      VARCHAR(50) NOT NULL UNIQUE,
    active     BOOLEAN     NOT NULL
);

CREATE TABLE categories
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE products
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(20)  NOT NULL UNIQUE,
    price       DECIMAL(6, 2) NOT NULL,
    weight      INTEGER       NOT NULL,
    image_url   VARCHAR(255),
    description TEXT,
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE TABLE carts
(
    id         VARCHAR(20) PRIMARY KEY,
    user_phone VARCHAR(20),
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (user_phone) REFERENCES users (phone)
);

CREATE TABLE cart_items
(
    id         SERIAL PRIMARY KEY,
    cart_id    VARCHAR(20),
    product_id BIGINT,
    quantity   INTEGER NOT NULL,
    FOREIGN KEY (cart_id) REFERENCES carts (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TABLE orders
(
    id               SERIAL PRIMARY KEY,
    user_phone       VARCHAR(20),
    delivery_address VARCHAR(255),
    status           VARCHAR(50),
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed_at     TIMESTAMP,
    FOREIGN KEY (user_phone) REFERENCES users (phone)
);

CREATE TABLE order_items
(
    id             SERIAL PRIMARY KEY,
    order_id       BIGINT,
    product_id     BIGINT,
    quantity       INTEGER       NOT NULL,
    relevant_price DECIMAL(6, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);
