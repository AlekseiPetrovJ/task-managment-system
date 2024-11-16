
# Система управления задачами

Реализованы REST методы:
- POST получение токена

Учетные записи хранятся в postgresql. Начальные учетные записи пользователя и администратора вносятся в БД посредством liquibase.

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