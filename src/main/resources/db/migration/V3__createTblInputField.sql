-- V3__createTblInputField.sql

CREATE TABLE input_field (
     input_field_id BIGINT AUTO_INCREMENT PRIMARY KEY,
     label VARCHAR(255),
     type ENUM('NUMBER', 'STRING', 'DATE', 'LONGTEXT', 'CHECKBOX', 'RADIO') NOT NULL,
     permission ENUM('READONLY', 'EDITABLE', 'REQUIRED', 'OPTIONAL', 'HIDDEN') NOT NULL,
     step_id BIGINT,
     user_id BIGINT,
     CONSTRAINT fk_user_input_field FOREIGN KEY (user_id) REFERENCES user(user_id)
);
