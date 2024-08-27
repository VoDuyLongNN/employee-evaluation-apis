CREATE TABLE IF NOT EXISTS token (
    token_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token_string VARCHAR(255),
    user_id BIGINT,
    expire DATETIME,
    CONSTRAINT fk_user_token FOREIGN KEY (user_id) REFERENCES user(user_id)
);
