-- schema.sql
CREATE TABLE IF NOT EXISTS users_type (
                                          usertype_id INT AUTO_INCREMENT PRIMARY KEY,
                                          user_type_name VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS users (
                                     user_id INT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_active BOOLEAN,
    registration_date DATE,
    user_type_id INT,
    CONSTRAINT fk_user_type FOREIGN KEY (user_type_id) REFERENCES users_type(usertype_id)
    );
