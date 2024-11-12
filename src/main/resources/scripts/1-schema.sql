/*
-- Drop all tables if they exist
DROP TABLE IF EXISTS public.users_roles;
DROP TABLE IF EXISTS public.teacher;
DROP TABLE IF EXISTS public.super_user;
DROP TABLE IF EXISTS public.student_marks;
DROP TABLE IF EXISTS public.student;
DROP TABLE IF EXISTS public.users;
DROP TABLE IF EXISTS public.students_payments;
DROP TABLE IF EXISTS public.payment;
DROP TABLE IF EXISTS public.examination CASCADE;
DROP TABLE IF EXISTS public.certificate;
DROP TABLE IF EXISTS public.course;
DROP TABLE IF EXISTS public.teacher_data;
DROP TABLE IF EXISTS public.super_user_data;
DROP TABLE IF EXISTS public.groups_students;
DROP TABLE IF EXISTS public.student_group;
DROP TABLE IF EXISTS public.student_data;
DROP TABLE IF EXISTS public.role;
DROP TABLE IF EXISTS public.keys;
*/

-- Recreate all tables
CREATE TABLE IF NOT EXISTS public.keys (
                                           id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                           password varchar(255)
);

ALTER TABLE public.keys OWNER TO course_project_owner;

CREATE TABLE IF NOT EXISTS public.role (
                                           id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                           name varchar(255)
);

ALTER TABLE public.role OWNER TO course_project_owner;

CREATE TABLE IF NOT EXISTS public.student_data (
                                                   id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                                   birth_date date,
                                                   first_name varchar(255),
                                                   last_name varchar(255),
                                                   balance real
);

ALTER TABLE public.student_data OWNER TO course_project_owner;

CREATE TABLE IF NOT EXISTS public.student_group (
                                                    id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY
);

ALTER TABLE public.student_group OWNER TO course_project_owner;

CREATE TABLE IF NOT EXISTS public.groups_students (
                                                      group_id integer NOT NULL REFERENCES public.student_group,
                                                      student_id integer NOT NULL REFERENCES public.student_data
);

ALTER TABLE public.groups_students OWNER TO course_project_owner;

CREATE TABLE IF NOT EXISTS public.super_user_data (
                                                      id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                                      birth_date date,
                                                      first_name varchar(255),
                                                      last_name varchar(255)
);

ALTER TABLE public.super_user_data OWNER TO course_project_owner;

CREATE TABLE IF NOT EXISTS public.teacher_data (
                                                   id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                                   birth_date date,
                                                   first_name varchar(255),
                                                   last_name varchar(255)
);

ALTER TABLE public.teacher_data OWNER TO course_project_owner;

CREATE TABLE IF NOT EXISTS public.course (
                                             id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                             description varchar(255),
                                             end_date date,
                                             language varchar(255),
                                             language_level varchar(255),
                                             name varchar(255),
                                             price real,
                                             start_date date,
                                             state varchar(255) CONSTRAINT course_state_check CHECK (state::text = ANY (ARRAY['CREATED', 'STARTED', 'FINISHED']::text[])),
                                             examination_id integer UNIQUE,
                                             student_group_id integer UNIQUE REFERENCES public.student_group,
                                             teacher_data_id integer REFERENCES public.teacher_data
);

ALTER TABLE public.course OWNER TO course_project_owner;

CREATE TABLE IF NOT EXISTS public.certificate (
                                                  id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                                  mark integer,
                                                  course_id integer REFERENCES public.course,
                                                  student_id integer REFERENCES public.student_data
);

ALTER TABLE public.certificate OWNER TO course_project_owner;

CREATE TABLE IF NOT EXISTS public.examination (
                                                  id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY
);

ALTER TABLE public.examination OWNER TO course_project_owner;

ALTER TABLE public.course
    ADD CONSTRAINT fklmuftu2a4r8qv2vbkdp1fhhnf FOREIGN KEY (examination_id) REFERENCES public.examination;

CREATE TABLE IF NOT EXISTS public.payment (
                                              id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                              payment real,
                                              course_id integer REFERENCES public.course,
                                              student_id integer REFERENCES public.student_data
);

ALTER TABLE public.payment OWNER TO course_project_owner;

CREATE TABLE IF NOT EXISTS public.students_payments (
                                                        student_data_id integer NOT NULL REFERENCES public.student_data,
                                                        debt_id integer NOT NULL REFERENCES public.payment
);

ALTER TABLE public.students_payments OWNER TO course_project_owner;

CREATE TABLE IF NOT EXISTS public.users (
                                            id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                            enabled boolean,
                                            username varchar(255),
                                            key_id integer UNIQUE REFERENCES public.keys
);

ALTER TABLE public.users OWNER TO course_project_owner;

CREATE TABLE IF NOT EXISTS public.student (
                                              id integer NOT NULL PRIMARY KEY REFERENCES public.users,
                                              student_data_id integer UNIQUE REFERENCES public.student_data
);

ALTER TABLE public.student OWNER TO course_project_owner;

CREATE TABLE IF NOT EXISTS public.student_marks (
                                                    examination_id integer NOT NULL REFERENCES public.examination,
                                                    mark integer,
                                                    student_id integer NOT NULL REFERENCES public.student,
                                                    PRIMARY KEY (examination_id, student_id)
);

ALTER TABLE public.student_marks OWNER TO course_project_owner;

CREATE TABLE IF NOT EXISTS public.super_user (
                                                 id integer NOT NULL PRIMARY KEY REFERENCES public.users,
                                                 super_user_data_id integer UNIQUE REFERENCES public.super_user_data
);

ALTER TABLE public.super_user OWNER TO course_project_owner;

CREATE TABLE IF NOT EXISTS public.teacher (
                                              id integer NOT NULL PRIMARY KEY REFERENCES public.users,
                                              teacher_data_id integer UNIQUE REFERENCES public.teacher_data
);

ALTER TABLE public.teacher OWNER TO course_project_owner;

CREATE TABLE IF NOT EXISTS public.users_roles (
                                                  user_id integer NOT NULL REFERENCES public.users,
                                                  role_id integer NOT NULL REFERENCES public.role
);

ALTER TABLE public.users_roles OWNER TO course_project_owner;