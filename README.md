# REST API для онлайн-аптеки ProPill

Проект представляет собой REST API для
онлайн-аптеки. [Реализация web-приложения для работы с API](https://github.com/jekasrs/online-pharmacy-client).

## Схема базы данных

![propill_scheme](https://github.com/byzaya/online-pharmacy/assets/78423459/5cfb7336-7c61-4b88-ae0f-e567f9a9483f)

## Механика работы онлайн-аптеки

В рамках онлайн-аптеки выделяются 4 категории пользователей:

* неавторизованные пользователи
* авторизованные пользователи
* врачи
* фармацевты(администраторы онлайн-аптеки)

В рамках онлайн-аптеки выделяются 3 категории препаратов:

* общие(доступны для заказа всеми категориями пользователей)
* рецептурные
* специальные(доступны только врачам определённой специальности)

### Карточка препарата

Карточка препарата содержит в себе изображение, наименование товара, стоимость, производитель,
способ применения, тип.
Введены следующие ограничения на формат этих полей:

* наименование, изготовитель: длина не более 100 символов латиницы/кириллицы/цифр/+тире/+процент
* стоимость: положительное число
* способ применения: не более 500 символов
* размер изображений не превышает 5 Мб

### Корзина

За каждым зарегистрированным пользователем закреплена корзина, с помощью которой реализовано
создание заказов

### Аптека для самовывоза

Получение заказов реализуется в аптеках самовывоза. Для каждой аптеки указывается название, адрес,
контактный номер
телефона, часы работы. Также ведется учет о наличии препаратов в аптеках.

### Заказ

При создании заказа происходит удаление соответствующих позиций из корзины пользователя и изменение
данных о наличии
препарата в аптеке.

## Стек технологий

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
[![Backblazeb2](https://img.shields.io/badge/Backblazeb2-<COLOR>.svg)](https://shields.io/)

## Запуск и установка

Для запуска необходимо ввести следующие данные в resources/application.properties

| Переменная                 | Описание                       |
|----------------------------|--------------------------------|
| spring.datasource.username | Имя пользователя в PostgreSQL  | 
| spring.datasource.password | Пароль пользователя PostgreSQL |
| bucketUrl                  | Ссылка на bucket в Backblazeb2 |
| userAgent                  | Имя пользователя в Backblazeb2 |
| appKey                     | applicationKey в Backblazeb2   |
| appKeyId                   | keyID в Backblazeb2            |
| bucketId                   | Bucket ID в Backblazeb2        |
