-- schema.sql
CREATE TABLE product
(
    id          INT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    price       DOUBLE       NOT NULL
);
CREATE TABLE product_output
(
    id          INT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    price       DOUBLE       NOT NULL
);
-- schema.sql
CREATE TABLE os_product
(
    id          INT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    price       DOUBLE       NOT NULL,
    tax         DOUBLE       NOT NULL,
    discount    DOUBLE       NOT NULL,
    taxPercent  DOUBLE       NOT NULL,
    finalPrice  DOUBLE       NOT NULL
);

-- data.sql
INSERT INTO product (id, name, description, price)
VALUES  (1, 'Gaming Mouse', 'High precision wireless gaming mouse', 59.99),
        (2, 'Mechanical Keyboard', 'RGB backlit mechanical keyboard with blue switches', 89.99),
        (3, 'Gaming Headset', 'Surround sound gaming headset with noise-canceling microphone', 79.99),
        (4, 'Graphics Card', 'NVIDIA RTX 3070 Ti GPU with 8GB GDDR6 memory', 499.99),
        (5, 'Gaming Chair', 'Ergonomic gaming chair with adjustable armrests and lumbar support', 129.99),
        (6, '4K Gaming Monitor', '27-inch 4K UHD gaming monitor with 144Hz refresh rate', 349.99),
        (7, 'Gaming Laptop', '15.6-inch gaming laptop with Intel i7, 16GB RAM, and RTX 3060', 1249.99),
        (8, 'VR Headset', 'Virtual reality headset with 110-degree field of view and motion controllers', 299.99),
        (9, 'Game Console', 'Latest generation game console with 1TB SSD and 4K support', 499.99),
        (10, 'Gaming Desk', 'Large gaming desk with cable management and RGB lighting', 199.99),
        (11, 'Streaming Microphone', 'High-quality condenser microphone for game streaming', 89.99),
        (12, 'External SSD', '1TB portable external SSD with USB-C interface for fast data transfer', 159.99),
        (13, 'Capture Card', 'HD capture card for streaming games with 4K 60fps support', 129.99),
        (14, 'Wireless Controller', 'Ergonomic wireless controller compatible with PC and consoles', 49.99),
        (15, 'RGB Gaming Mouse Pad', 'Large RGB gaming mouse pad with customizable lighting', 29.99),
        (16, 'Gaming Router', 'High-performance Wi-Fi 6 gaming router with low latency mode', 199.99),
        (17, 'Portable Gaming Monitor', '15.6-inch Full HD portable gaming monitor with USB-C', 179.99),
        (18, 'Custom Gaming PC', 'Prebuilt gaming PC with AMD Ryzen 7, 32GB RAM, and RTX 3080', 1899.99),
        (19, 'Game Storage Tower', 'Game storage tower for storing up to 20 game cases', 39.99),
        (20, 'Gaming Glasses', 'Anti-glare gaming glasses to reduce eye strain during long sessions', 25.99),
        (21, 'Cooling Pad', 'Laptop cooling pad with adjustable fan speed and RGB lights', 34.99),
        (22, 'Gaming Headphone Stand', 'Headphone stand with built-in USB hub and RGB lighting', 24.99),
        (23, 'Arcade Stick', 'Professional arcade stick for fighting games with customizable buttons', 129.99),
        (24, 'Sound Bar', 'Compact sound bar with built-in subwoofer for immersive gaming audio', 119.99),
        (25, 'Gamer’s Backpack', 'Durable backpack designed for carrying gaming gear and accessories', 69.99);
