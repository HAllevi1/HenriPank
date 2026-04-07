# HenriPank API

HenriPank is a simple, foundational REST API built with Spring Boot for managing banking users and their associated accounts.

This project demonstrates core backend development concepts, including layered architecture, relational database mapping (ORM), data validation, and the Data Transfer Object (DTO) pattern.

## 🛠️ Technologies Used
* **Java 17+** * **Spring Boot 3.x**
* **Spring Web** (REST APIs)
* **Spring Data JPA / Hibernate** (Database access and ORM)
* **Jakarta Validation** (Input validation)

## 🏗️ Architecture
The application follows a standard layered architecture:
* **Controllers:** Handle incoming HTTP requests and route them to services.
* **Services:** Contain the core business logic (e.g., automatically generating a bank account when a new user registers).
* **Repositories:** Interface with the database using Spring Data JPA.
* **Models (Entities):** Represent the database tables (`bank_users`, `Account`) with a mapped One-to-Many / Many-to-One relationship.
* **DTOs:** Filter and shape the data being returned to the client (e.g., `AccountDTO` hides the internal database ID and user reference).

---

## 🚀 API Endpoints

### 1. Add a New User
Creates a new user in the system. Upon successful creation, the application automatically generates a new bank account with a random `EE` IBAN and a starting balance of `0.0` for the user.

* **URL:** `/api/user/add`
* **Method:** `POST`
* **Content-Type:** `application/json`

**Request Body:**
```json
{
  "name": "Liisu",
  "email": "Liisu@gmail.com"
}