-- Insert sample companies
INSERT INTO company (company_name, address)
VALUES ('FPT Software Quy Nhon', '12 Dai Lo Khoa Hoc, Ghenh Rang, Quy Nhon, Binh Dinh, Vietnam'),
       ('Company A', '123 Main St, Any town, USA'),
       ('Company B', '456 Elm St, Any town, USA'),
       ('Company C', '789 Oak St, Any town, USA');
-- Create table for employees
CREATE TABLE employee
(
    employee_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_id  BIGINT       NOT NULL,
    name        VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL,
    position    VARCHAR(255),
    FOREIGN KEY (company_id) REFERENCES company (company_id)
);
-- Insert 10 sample employees for different companies
INSERT INTO employee (company_id, name, email, position)
VALUES (2, 'John Doe', 'john.doe@companya.com', 'Developer'),
       (3, 'Jane Smith', 'jane.smith@companya.com', 'Designer'),
       (2, 'Emily Davis', 'emily.davis@companya.com', 'Manager'),
       (2, 'Michael Brown', 'michael.brown@companyb.com', 'Developer'),
       (2, 'Sarah Wilson', 'sarah.wilson@companyb.com', 'Designer'),
       (2, 'David Jones', 'david.jones@companyb.com', 'Manager'),
       (3, 'Daniel Garcia', 'daniel.garcia@companyc.com', 'Developer'),
       (4, 'Laura Martinez', 'laura.martinez@companyc.com', 'Designer'),
       (3, 'Kevin Anderson', 'kevin.anderson@companyc.com', 'Manager'),
       (4, 'Rachel Taylor', 'rachel.taylor@companyc.com', 'Developer');
-- Create table for event employees
CREATE TABLE event_employee
(
    event_employee_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_id          BIGINT NOT NULL,
    employee_id       BIGINT NOT NULL,
    added_date        DATE   NOT NULL,
    FOREIGN KEY (event_id) REFERENCES event (event_id),
    FOREIGN KEY (employee_id) REFERENCES employee (employee_id)
);
