CREATE TABLE IF NOT EXISTS company
(
    company_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    address      VARCHAR(255) NOT NULL,
    description varchar(255) DEFAULT '',
    created_at timestamp  DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp  DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS role
(
    role_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name ENUM ('ROLE_SYSTEM','ROLE_ADMIN','ROLE_USER') NOT NULL
);

CREATE TABLE IF NOT EXISTS user
(
    user_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name  VARCHAR(50)  NOT NULL,
    full_name  VARCHAR(100) NOT NULL,
    email      VARCHAR(100) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    company_id BIGINT
);

ALTER TABLE user
    ADD CONSTRAINT fk_user_company FOREIGN KEY (company_id) REFERENCES company (company_id);

CREATE TABLE IF NOT EXISTS user_has_role
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role (role_id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS event_category
(
    evt_category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_name   VARCHAR(255) NOT NULL,
    description     TEXT,
    company_id      BIGINT,
    FOREIGN KEY (company_id) REFERENCES company (company_id)
);

CREATE TABLE IF NOT EXISTS event
(
    event_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_name VARCHAR(255),
    start_date DATE NOT NULL,
    end_date   DATE NOT NULL,
    company_id BIGINT,
    status     ENUM ('ACTIVE', 'UNAVAILABLE'),
    CONSTRAINT fk_company FOREIGN KEY (company_id) REFERENCES company (company_id)
);

CREATE TABLE IF NOT EXISTS event_has_category
(
    event_id        BIGINT,
    evt_category_id BIGINT,
    PRIMARY KEY (event_id, evt_category_id),
    CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES event (event_id),
    CONSTRAINT fk_category FOREIGN KEY (evt_category_id) REFERENCES event_category (evt_category_id)
);
