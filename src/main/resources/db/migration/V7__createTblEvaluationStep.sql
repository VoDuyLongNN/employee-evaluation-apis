CREATE TABLE evaluation_step (
    step_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    step_name VARCHAR(255) NOT NULL,
    event_id BIGINT,
    level INT NOT NULL,
    CONSTRAINT fk_step_event
        FOREIGN KEY (event_id)
        REFERENCES event(event_id)
        ON DELETE SET NULL
);