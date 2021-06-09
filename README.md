# Proposal Service

## General info
Service allowing management of proposals together with preservation their full history of changes.

## How to start application:
Go to project directory and run:
```
mvn spring-boot:run
```
Application will run on port 9999

## API
Service definition is available under:
```
http://localhost:9999/swagger-ui/
```

## Database
Project uses the built-in H2 database. The database can be viewed through the console available under: 
```
http://localhost:9999/h2
```
Credentials are defined in application.properties file
Default:
- url: jdbc:h2:mem:testdb
- username: sa
- password: sa

## Assumptions
- The service does not provide the option to delete proposals. It is assumed that in the future a cron job will delete outdated proposals with status DELETED.
- It is assumed that, this is an internal service. Otherwise, the ID implementation will be replaced with an unpredictable UUID for security purposes.
- It is assumed that, exposing the number of created proposals and published proposals is not the business confidentiality.

If you change any of the assumptions, the implementation is not complex.
