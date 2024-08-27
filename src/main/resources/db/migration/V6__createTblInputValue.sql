-- Table for input_value
CREATE TABLE IF NOT EXISTS input_value (
     input_value_id BIGINT AUTO_INCREMENT PRIMARY KEY,
     input_field_id BIGINT,
     user_id BIGINT,
     value VARCHAR(255),
     CONSTRAINT unique_input_field_user UNIQUE (input_field_id, user_id),
     CONSTRAINT fk_ivalue_ifield FOREIGN KEY (input_field_id) REFERENCES input_field(input_field_id),
     CONSTRAINT fk_ivalue_user FOREIGN KEY (user_id) REFERENCES user(user_id)
);
