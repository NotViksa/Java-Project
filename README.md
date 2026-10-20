# Хотелски резервации

Проект по Java със Spring Boot за хотелски резервации. API-то работи с хотели, стаи, екстри, гости и резервации. Една стая не може да се резервира два пъти за застъпващи се дати, а цената се смята според сезона.

## Технологии

- Java 25 и Spring Boot 4.1.1
- PostgreSQL
- Spring Data JPA, MapStruct, Lombok, Jakarta Validation
- Swagger UI (springdoc)

## Таблици

| Entity | Полета | Връзки |
| --- | --- | --- |
| `Hotel` | id, name, city, address, description | една към много с `Room` |
| `Room` | id, roomNumber, type, capacity, pricePerNight | много към едно с `Hotel`, много към много с `RoomExtra` |
| `RoomExtra` | id, name | много към много с `Room` |
| `Season` | id, name, startDate, endDate, priceMultiplier | без връзки |
| `Guest` | id, firstName, lastName, email, phoneNumber | една към много с `Reservation` |
| `Reservation` | id, checkInDate, checkOutDate, guestCount, totalPrice, status, createdAt | много към едно с `Guest` и `Room` |

`RoomExtra` са екстрите на стаята - WiFi, телевизор, климатик, закуска. Връзката е Many-to-Many и се пази в join table `room_extras`.

`Season` е период с различна цена. Например "Лято 2027" с `priceMultiplier = 1.5` прави нощувките 50% по-скъпи.

Така има 6 таблици, една Many-to-Many връзка и няколко Many-to-One.

## Бизнес логика

- Една резервация е за една стая и един гост.
- `checkOutDate` трябва да е след `checkInDate`, а датите не могат да са в миналото.
- `guestCount` не може да е повече от капацитета на стаята.
- Резервацията започва като `CONFIRMED`.
- От `CONFIRMED` може да мине в `CHECKED_IN` на или след датата за настаняване.
- От `CHECKED_IN` може да мине в `CHECKED_OUT` след датата за настаняване.
- `CONFIRMED` може да се откаже преди настаняване. Настанена или приключила резервация не се отказва.
- Ако смяната на статуса не е позволена, API-то връща 409.

### Свободни стаи и двойна резервация

За свободните стаи има custom `@Query`, който връща стаи без резервация за застъпващи се дати. Две резервации се застъпват, когато `existing.checkInDate < new.checkOutDate` и `existing.checkOutDate > new.checkInDate`.

При създаване на резервация стаята се заключва в transaction. Ако двама души резервират едновременно, вторият изчаква и накрая получава 409.

### Сезонни цени

- Всяка нощувка се проверява дали влиза в `Season`.
- Ако влиза, цената е `pricePerNight * priceMultiplier`; ако не - базовата `pricePerNight`.
- За 7 или повече нощувки има 10% отстъпка.
- `totalPrice` се пази в резервацията и не се преизчислява при промяна на сезоните.
- Сезоните не могат да се застъпват. При застъпване API-то връща 409.
- `startDate` трябва да е преди или равна на `endDate`.

Пример: 2 нощувки извън сезон по 100 € и 3 нощувки в сезон с множител 1.5 излизат `2 * 100 + 3 * 150 = 650 €`.

## Endpoint-и

| Method | Path | Описание |
| --- | --- | --- |
| GET, POST | `/api/hotels` | Списък и създаване на хотели |
| GET, PUT, DELETE | `/api/hotels/{id}` | Четене, редакция и изтриване на хотел |
| GET, POST | `/api/rooms` | Всички стаи (може и по `hotelId`) и създаване на стая |
| GET, PUT, DELETE | `/api/rooms/{id}` | Четене, редакция и изтриване на стая |
| GET | `/api/rooms/available` | Свободни стаи по хотел, дати и брой гости |
| GET, POST | `/api/extras` | Списък и създаване на екстри |
| GET, PUT, DELETE | `/api/extras/{id}` | Четене, редакция и изтриване на екстра |
| GET, POST | `/api/seasons` | Списък и създаване на сезони |
| GET, PUT, DELETE | `/api/seasons/{id}` | Четене, редакция и изтриване на сезон |
| GET, POST | `/api/guests` | Списък и създаване на гости |
| GET, PUT, DELETE | `/api/guests/{id}` | Четене, редакция и изтриване на гост |
| POST | `/api/reservations` | Създаване на резервация |
| GET | `/api/reservations` | Всички резервации (може и по `guestId`) |
| GET | `/api/reservations/{id}` | Една резервация по id |
| PATCH | `/api/reservations/{id}/cancel` | Отказване на резервация |
| PATCH | `/api/reservations/{id}/check-in` | Настаняване |
| PATCH | `/api/reservations/{id}/check-out` | Освобождаване на стаята |

Ако хотелът има стаи, стаята има резервации или гостът има резервации, изтриването връща 409.

## Как да стартираш

### 1. Java 25

Проектът е на Java 25. Изтегли Temurin 25 от [Adoptium](https://adoptium.net/temurin/releases/?version=25). По-стара версия може да не тръгне.

След инсталацията провери в терминал:

```
java -version
```

Трябва да видиш версия 25. Ако не, настрой `JAVA_HOME`:

- [Инструкция за Windows](https://javatutorial.net/set-java-home-windows-10/)

### 2. PostgreSQL

- [Изтегли PostgreSQL за Windows](https://www.postgresql.org/download/windows/)
- [Инсталация и настройка](https://www.postgresqltutorial.com/install-postgresql/)

Използваме PostgreSQL 18. При инсталацията запомни паролата на `postgres`. След това отвори pgAdmin, свържи се към `postgres` и в Query Tool изпълни:

```sql
CREATE ROLE java_project LOGIN PASSWORD 'твоята_парола';
CREATE DATABASE java_project OWNER java_project;
```

### 3. Git

Инсталирай Git от [git-scm.com](https://git-scm.com/downloads).

### 4. Изтегли проекта

Приеми поканата за GitHub repo-то. После в терминал:

```
git clone https://github.com/NotViksa/Java-Project.git
cd Java-Project
```

Отвори папката във Visual Studio (File → Open → Folder). Терминалът е на View → Terminal. Ако нямаш Visual Studio, изтегли го от [тук](https://visualstudio.microsoft.com/downloads/).

### 5. Настрой `.env`

Копирай `.env.example` като `.env`:

```
Copy-Item .env.example .env
```

Отвори `.env` и сложи твоите данни:

```
DB_URL=jdbc:postgresql://localhost:5432/java_project
DB_USERNAME=java_project
DB_PASSWORD=твоята_парола
```

`.env` е в `.gitignore` и не се качва в GitHub.

### 6. Стартирай

```
.\mvnw spring-boot:run
```

Първото стартиране може да отнеме няколко минути, защото `mvnw` сваля Maven и зависимостите. След това отвори [Swagger UI](http://localhost:8080/swagger-ui.html).

## Кой какво прави

Всеки си работи по неговите неща. Ако нещо трябва да се промени по кода на другия, първо се питаме.

### Виктор - основа и резервации

- Правя таблиците и връзките между тях.
- Попълвам примерните данни.
- Пиша заявките към базата, включително тази за свободни стаи.
- Занимавам се с резервациите и статусите им.
- Правя защитата от двойна резервация.
- Отговарям за общите съобщения за грешки.
- Настройвам Swagger.
- Пиша всички автоматични тестове (unit и integration).

### Недежда - CRUD и цени

- Правиш DTO-тата - класовете, които API-то приема и връща.
- Правиш преобразуването между тях и таблиците.
- Правиш CRUD за хотели, стаи, екстри, сезони и гости.
- Слагаш валидации на входните данни.
- Смяташ цената според сезона и отстъпката за 7+ нощувки.
- Проверяваш, че сезоните не се застъпват.
- Описваш endpoint-ите в Swagger.

### Заедно

- Преглеждаме си кода.
- Пробваме endpoint-ите през Swagger.
- Довършваме README.

## Ред на работа

1. Таблиците и заявките към базата. (Виктор)
2. DTO-тата и преобразуването. (Недежда)
3. Резервации, свободни стаи и защита от двойна резервация. (Виктор)
4. CRUD-ът и цените. (Недежда)
5. Автоматичните тестове. (Виктор)
6. Заедно: Swagger и README.

След всяка стъпка проверяваме каквото е готово през Swagger.
