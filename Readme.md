[![spring-jwt with Maven](https://github.com/AlekseiPetrovJ/task-managment-system/actions/workflows/maven.yml/badge.svg)](https://github.com/AlekseiPetrovJ/task-managment-system/actions/workflows/maven.yml)

# Система управления задачами
Администратор и автор задачи могут изменять любые параметры в задаче.
Пользователи не администраторы могут изменять только статус задачи и комментарий 
только в задачах в которых они являются исполнителями.

Реализованы REST методы:
- POST получение токена
- GET получения всех задач c пагинацией
- POST добавление новой задачи
- PUT изменение существующей задачи

Задачи и пользователи хранятся в postgresql. Демо данные внесены в БД посредством liquibase.

Применены: Java 17+, Spring Boot, PostgreSQL, Spring Security, Liquibase, Docker-compose.
В качестве CI настроен github action.


## [Техническое задание](tz.txt)

# Запуск приложения
## Требования
Установленный maven, docker, docker compose

## Запуск
1) Скачайте проект из ветки master
2) в командной строке (cmd/bash) перейдите в каталог проекта

windows: `cd C:\Users\user\Downloads\<КаталогПроекта>`

linux: `cd ~/Downloads/<КаталогПроекта>`

3) Соберите докер образ:

windows: 
```bash
./mvnw.cmd -B clean package dockerfile:build
```

linux: 
```bash
./mvnw -B clean package dockerfile:build
```

4) Запуск проекта:
```bash
docker compose up
```

## OpenAPI
[swagger-ui](http://127.0.0.1:8080/swagger-ui/index.html)

## Credentials

Пользователь с полными правами:

```
"email": "admin@gmail.com",
"password": "admin"
```

Пользователь с ограниченными правами:

```
"email": "user@gmail.com",
"password": "user"
```