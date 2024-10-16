-- schema.sql
CREATE TABLE product (
                         id INT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         description VARCHAR(1000),
                         price DOUBLE NOT NULL
);
CREATE TABLE product_output (
                         id INT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         description VARCHAR(1000),
                         price DOUBLE NOT NULL
);
-- data.sql
INSERT INTO product (id, name, description, price) VALUES
                                                       (1, 'Gaming Mouse', 'High precision wireless gaming mouse', 59.99),
                                                       (2, 'Mechanical Keyboard', 'RGB backlit mechanical keyboard with blue switches', 89.99),
                                                       (3, 'Gaming Headset', 'Surround sound gaming headset with noise-canceling microphone', 79.99),
                                                       (4, 'Graphics Card', 'NVIDIA RTX 3070 Ti GPU with 8GB GDDR6 memory', 499.99),
                                                       (5, 'Gaming Chair', 'Ergonomic gaming chair with adjustable armrests and lumbar support', 129.99);
