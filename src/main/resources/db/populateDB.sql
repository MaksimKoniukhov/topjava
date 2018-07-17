DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, datetime, description, calories) VALUES
  ('100000', '2018-07-17 09:04:35', 'Завтрак', '500'),
  ('100000', '2018-07-17 13:04:00', 'lunch', '1000'),
  ('100001', '2018-07-17 19:00:30', 'Ужин', '501');