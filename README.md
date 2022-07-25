Java Enterprise Online Project 
===============================
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/0be861a89b9f412ebd3f2e75dffb9519)](https://www.codacy.com/gh/ppichugin/topjava/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ppichugin/topjava&amp;utm_campaign=Badge_Grade)

Разработка полнофункционального Spring/JPA Enterprise приложения c авторизацией и правами доступа на основе ролей с использованием наиболее популярных инструментов и технологий Java: Maven, Spring MVC, Security, JPA(Hibernate), REST(Jackson), Bootstrap (css,js), datatables, jQuery + plugins, Java 8 Stream and Time API и хранением в базах данных Postgresql и HSQLDB.

![topjava_structure](https://user-images.githubusercontent.com/13649199/27433714-8294e6fe-575e-11e7-9c41-7f6e16c5ebe5.jpg)

    Когда вы слышите что-то, вы забываете это.
    Когда вы видите что-то, вы запоминаете это.
    Но только когда вы начинаете делать это,
    вы начинаете понимать это

    Старинная китайская поговорка

## <a href="description.md">Описание и план проекта</a>
### <a href="http://topjava.herokuapp.com/" target=_blank>Демо разрабатываемого приложения</a>
### [Изменения проекта (Release Notes)](ReleaseNotes.md)

---

## Test curls for REST API:
* Get all meal: 
```console
curl -X GET http://localhost:8080/topjava/rest/meals
```
* Get meal filtered:
```console
curl -X GET "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=10:00&endDate=2020-01-31&endTime=13:05"
```
* Get meal with null filtered parameters:
```console
curl -X GET http://localhost:8080/topjava/rest/meals/filter
```
* Delete meal:
```console
curl -X DELETE http://localhost:8080/topjava/rest/meals/100009
```
* Create new meal:
```console
curl -H "Content-Type: application/json" -X POST http://localhost:8080/topjava/rest/meals/ -d "{\"dateTime\": \"2022-07-23T17:40:00\", \"description\": \"Обед\", \"calories\": 525}"
```
* Update meal:
```console
curl -H "Content-Type: application/json" -X PUT http://localhost:8080/topjava/rest/meals/100007 -d "{\"dateTime\": \"2020-01-31T13:14:00\", \"description\": \"Обед обновленный2\", \"calories\": 1113}"
```

