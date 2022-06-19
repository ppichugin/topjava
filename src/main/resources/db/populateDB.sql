DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2022-6-17 10:00:00', 'Завтрак', 500),
       (100000, '2022-6-17 13:00:00', 'Обед', 1000),
       (100000, '2022-6-17 20:00:00', 'Ужин', 500),
       (100000, '2022-6-18 00:00:00', 'Еда на граничное значение', 100),
       (100000, '2022-6-18 10:00:00', 'Завтрак', 1000),
       (100000, '2022-6-18 13:00:00', 'Обед', 500),
       (100000, '2022-6-18 20:00:00', 'Ужин', 410),
       (100001, '2022-6-19 08:00:00', 'Завтрак админ', 300),
       (100001, '2022-6-19 14:00:00', 'Обед админ', 700),
       (100001, '2022-6-19 19:00:00', 'Ужин админ', 1000);
