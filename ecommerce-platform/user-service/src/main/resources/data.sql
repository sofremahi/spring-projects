
CREATE TABLE IF NOT EXISTS tbl_role (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS tbl_user (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    username VARCHAR(255) NOT NULL,
                                    password VARCHAR(255) NOT NULL,
                                    email VARCHAR(255) NOT NULL,
                                    created_at TIMESTAMP,
                                    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_roles (
                                          user_id BIGINT,
                                          role_id BIGINT,
                                          PRIMARY KEY (user_id, role_id),
                                          FOREIGN KEY (user_id) REFERENCES tbl_user(id),
                                          FOREIGN KEY (role_id) REFERENCES tbl_role(id)
);


INSERT INTO tbl_role (name) VALUES ('ADMIN'), ('USER');


INSERT INTO tbl_user (username, password, email, created_at, updated_at)
VALUES ('alimh', '$2a$12$Su2HMJPAbhLct5UBtJ1WDOV6lY/odEe.Kz7prh.wkD/FGcRSSrNQq', 'alimh@example.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1), (1, 2);
