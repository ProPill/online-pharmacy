INSERT INTO type(id, name)
VALUES (-1, 'common'),
       (-2, 'receipt'),
       (-3, 'special');

INSERT INTO role(id, name)
VALUES (-1, 'пользователь'),
       (-2, 'врач'),
       (-3, 'фармацефт');

INSERT INTO speciality(id, name)
VALUES (-1, 'терапевт'),
       (-2, 'кардиолог');

INSERT INTO pharmacy (id, name, address, work_time, phone)
VALUES (-1, 'Здоровье',
        'ул. Ленина, 10',
        'Пн-Пт: 9:00-18:00, Сб: 10:00-15:00',
        '+7 (123) 456-7890'),
       (-2, 'Фармация', 'пр. Победы, 25',
        'Пн-Вс: 8:00-22:00',
        '+7 (987) 654-3210');

INSERT INTO item (info, price, id, speciality_id, type_id, manufacturer, name, picture_url)
VALUES ('Способ применения: Применяется внутрь. Дозировка: По 1 таблетке 1 раз в сутки, утром.',
        650, -1, NULL, -2, 'Фармстандарт-Лексредства, Россия',
        'Афобазол Ретард таблетки с пролонг высвобождением покрыт.плен.об. 30 мг 20 шт',
        'https://f003.backblazeb2.com/file/propill/afobazol_20.jpg'),
       ('Способ применения: Рекомендуется закапывать Артелак Баланс в конъюнктивальный мешок каждого глаза.',
        769, -2, -1, -3, 'Др. Герхард Манн ХФП, Германия',
        'Артелак Баланс раствор офтальмологический увлажняющий 10 мл 1 шт',
        'https://f003.backblazeb2.com/file/propill/artelak_balans.jpg');

INSERT INTO pharmacy_to_item (quantity, id, item_id, pharmacy_id)
VALUES (10, -1, -1, -1),
       (10, -2, -1, -2),
       (100, -3, -2, -1);

CREATE EXTENSION IF NOT EXISTS pgcrypto;

INSERT INTO user_account (id, full_name, password_hash, phone, role_id, speciality_id)
VALUES (-1, 'Иванов Иван Иванович', digest('123456', 'sha256'), '+79260567450', -1, NULL),
       (-2, 'Глазов Степан Фёдорович', digest('654321', 'sha256'), '+79310367450', -2, -1),
       (-3, 'Главный Пётр Петрович', digest('12345', 'sha256'), '+79510367450', -3, NULL),
       (-4, 'Я тестовый пользователь', digest('12345', 'sha256'), '+79510367457', -1, NULL);

INSERT INTO orders (creation_date, delivery_date, sum_price, id, pharmacy_id, user_id)
VALUES ('2024-01-11', '2024-01-16', 100500, -1, -1, -1),
       ('2024-01-07', '2024-01-10', 333, -2, -1, -1),
       ('2022-01-07', '2022-01-10', 333, -3, -1, -1),
       ('2022-01-07', '2022-01-10', 333, -4, -1, -2);

INSERT INTO order_to_item (quantity, id, item_id, order_id)
VALUES (1, -1, -1, -1),
       (4, -2, -2, -2),
       (3, -3, -1, -3),
       (10, -4, -2, -3),
       (10, -5, -2, -4);

INSERT INTO cart (id, user_id)
VALUES (-1, -1),
       (-2, -2),
       (-3, -3),
       (-4, -4);

INSERT INTO cart_to_item (quantity, cart_id, id, item_id)
VALUES (2, -1, -1, -1),
       (1, -1, -2, -2),
       (1, -2, -3, -2),
       (1, -4, -4, -2);
