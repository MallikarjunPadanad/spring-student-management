# Spring Student Management

Student CRUD app built with Spring Boot, Spring MVC, Spring JDBC (`JdbcTemplate`), Thymeleaf and PostgreSQL.

## Requirements
- Java 17+
- Maven 3.9+
- PostgreSQL with a database `appdb` and a user `appuser`

## Run
```bash
mvn spring-boot:run
```
Open http://localhost:8080

The `students` table is created on startup from `src/main/resources/schema.sql`.
If the table is empty, five sample students are added from `data.sql`.

Database settings live in `src/main/resources/application.properties`.

## Structure
```
in.strikes
├── StudentManagementApplication
├── config        WebConfig (redirects / to /students)
├── controller    StudentController (Spring MVC)
├── service       StudentService (business rules, transactions)
├── repository    StudentRepository (JdbcTemplate SQL)
├── model         Student, StudentStats
└── exception     custom exceptions + GlobalExceptionHandler
```
