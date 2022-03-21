INSERT INTO user_info (email) VALUES
('roman.s.popov@gmail.com'),
('roman.s.popov2@gmail.com'),
('roman.s.popov3@gmail.com');

INSERT INTO images (url) VALUES
('c:\1.jpg'),
('c:\2.jpg'),
('c:\3.jpg');

INSERT INTO USERS (username, password, enabled, last_password_reset_date, user_info_id, image_id) VALUES
('admin', '$2a$10$zuI3P8hoZNkFGR2dDPW9juA1C1xIHBUNrKMGqjjaEKsLTwjJkKoNa', true, CURRENT_TIMESTAMP, 1, 1),
('siva', '$2a$10$LskLrNP6m.dEpXYjT41lRePseXJEjhd6.sPH2Z5GbbShtaFRWoeYq', true, CURRENT_TIMESTAMP, 2, 2),
('siva2', '$2a$10$LskLrNP6m.dEpXYjT41lRePseXJEjhd6.sPH2Z5GbbShtaFRWoeYq', true, CURRENT_TIMESTAMP, 3, 3);

INSERT INTO ROLES (id, name) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

INSERT INTO USER_ROLE (user_id, role_id) VALUES
(1, 1),
(2, 1),
(2, 2);