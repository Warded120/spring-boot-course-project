INSERT INTO keys (id, password) VALUES (1, '$2a$10$DbuPbmj.VnxzfoO5YfEbJez.xYRRV9wJivpK6J9VnSH4XGi/wI5sy');

INSERT INTO users (id, username, key_id, enabled)
VALUES (1, 'admin@gmail.com', 1, true);

INSERT INTO super_user_data (id, first_name, last_name, birth_date)
VALUES (1, 'adminnnn', 'admin', '2005-05-18');

INSERT INTO super_user (id, super_user_data_id)
VALUES (1, 1);

INSERT INTO role (id, name)
VALUES (1, 'ROLE_OPERATOR'),
       (2, 'ROLE_ADMIN');

INSERT INTO users_roles (user_id, role_id) VALUES
                                               (1, 1),
                                               (1, 2);

BEGIN;

-- Insert role 'TEACHER' if not exists
INSERT INTO public.role (id, name)
VALUES (3, 'ROLE_TEACHER');

-- Insert key for user
INSERT INTO public.keys (id, password)
VALUES (2, '$2a$10$DbuPbmj.VnxzfoO5YfEbJez.xYRRV9wJivpK6J9VnSH4XGi/wI5sy');

-- Insert teacher's personal data
INSERT INTO public.teacher_data (id, birth_date, first_name, last_name)
VALUES (2, '1980-05-15', 'John', 'Doe');

-- Insert user for teacher
INSERT INTO public.users (id, enabled, username, key_id)
VALUES (2, TRUE, 'johnDoe@gmail.com', 2);

-- Insert teacher linking user and personal data
INSERT INTO public.teacher (id, teacher_data_id)
VALUES (2, 2);

-- Assign the 'TEACHER' role to the user
INSERT INTO public.users_roles (user_id, role_id)
VALUES (2, 3);

COMMIT;
