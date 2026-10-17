
# Banking Transaction and Fraud Detection System

A REST API built using Java 17, Spring Boot, Spring Security,
Spring Data JPA, Hibernate, and MySQL.

## Features

- User registration and authentication
- Role-based authorization
- Customer and account management
- Banking transactions and transfers
- Rule-based fraud detection
- Fraud alerts and audit logging
- Docker deployment
- Unit and integration testing

## Prerequisites

- Java 17+
- Maven 3.9+
- MySQL 8+
- Docker Desktop (optional)

## Run locally

1. Create the MySQL database `banking_db`.
2. Configure the database and JWT settings.
3. Run:

   mvn spring-boot:run

## Run using Docker

1. Copy `.env.example` to `.env`.
2. Replace all placeholder secrets.
3. Run:

   docker compose up --build

## API documentation

http://localhost:8080/swagger-ui.html

## Security

Never commit credentials or signing keys.
This project is a learning prototype, not a production banking platform.
