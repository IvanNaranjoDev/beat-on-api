-- Datos para 'roles'
INSERT IGNORE INTO roles (id, role_name) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_MANAGER'),
(3, 'ROLE_USER');

-- avatares
INSERT IGNORE INTO avatars (id, path_url) VALUES
(1, 'images/profile-1.png');

-- Datos para 'users'. La contraseña de cada usuario es password
INSERT IGNORE INTO users (id, username, email, passcode, enabled, avatar_id) VALUES
(1, 'admin', 'admin@gmail.com', '$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye', true, 1),
(2, 'manager', 'manager@gmail.com', '$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye', true, 1),
(3, 'normal', 'normal@gmail.com', '$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye', true, 1);

-- Asignación de roles
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(1, 1);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(2, 2);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(3, 3);