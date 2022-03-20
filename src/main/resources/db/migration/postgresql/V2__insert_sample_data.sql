INSERT INTO user_info (id, email) VALUES
(1, 'roman.s.popov@gmail.com'),
(2, 'roman.s.popov2@gmail.com'),
(3, 'roman.s.popov3@gmail.com');

INSERT INTO images (id, url) VALUES
(1, 'c:\1.jpg'),
(2, 'c:\2.jpg'),
(3, 'c:\3.jpg');

INSERT INTO USERS (id, username, password, enabled, last_password_reset_date, user_info_id, image_id) VALUES
(1, 'admin', '$2a$10$zuI3P8hoZNkFGR2dDPW9juA1C1xIHBUNrKMGqjjaEKsLTwjJkKoNa', true, CURRENT_TIMESTAMP, 1, 1),
(2, 'siva', '$2a$10$LskLrNP6m.dEpXYjT41lRePseXJEjhd6.sPH2Z5GbbShtaFRWoeYq', true, CURRENT_TIMESTAMP, 2, 2),
(3, 'siva2', '$2a$10$LskLrNP6m.dEpXYjT41lRePseXJEjhd6.sPH2Z5GbbShtaFRWoeYq', true, CURRENT_TIMESTAMP, 3, 3);

INSERT INTO ROLES (id, name) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

INSERT INTO USER_ROLE (user_id, role_id) VALUES
(1, 1),
(2, 1),
(2, 2);