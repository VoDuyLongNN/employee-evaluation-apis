INSERT INTO ROLE(role_name)
VALUES ('ROLE_SYSTEM'),('ROLE_ADMIN'),('ROLE_USER');

INSERT INTO USER(user_name,full_name, email, password)
VALUES('sysadmin', 'SYSTEM ADMIN', 'sysadmin@domain.com', '$2a$12$sWsGaeFjb.ymYM881HGaR.SKZ5ZsBiBGqZGKgjIislkUeU4l7Oe6q');

INSERT INTO user_has_role(user_id, role_id)
VALUES (1, 1), (1, 2), (1, 3);
