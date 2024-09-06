SELECT users.email AS username, users.password, roles.id AS roleId, roles.name
				FROM users
				INNER JOIN user_roles ON users.id = user_roles.user_id
				INNER JOIN roles ON roles.id = user_roles.role_id
				WHERE users.email = 'bruno@teste.com';

-- Insercao de ROLES
INSERT INTO roles (id,created_date, updated_date, name)
VALUES ('b82bc6cc-a78b-40ae-86cb-9f3533c6609c', '2024-08-24 12:00:00',
    '2024-08-24 12:00:00','ROLE_ADMIN');

-- Inserção USERS
INSERT INTO users (id, created_date, updated_date, first_name, last_name, birth_date, phone, email, password)
VALUES (
    'dfe2672e-dc25-4c4f-b34d-3583dd4ef52f',
    '2024-08-24 12:00:00',
    '2024-08-24 12:00:00',
    'Bruno',
    'Dias',
    '1995-01-26',
    '14991781010',
    'bruno@teste.com',
    '$2a$10$FuTU.rEr4GVOvhY6iIPQV.nq.6H3Goy1raXSN5Ux549uCd46ifeXq'
);


-- Insercao User_Role
INSERT INTO user_roles (user_id, role_id) VALUES ('dfe2672e-dc25-4c4f-b34d-3583dd4ef52f', 'b82bc6cc-a78b-40ae-86cb-9f3533c6609c');
