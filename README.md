# 🏦 HenriPank API

HenriPank is a robust REST API built with **Spring Boot 3** for managing banking users and secure financial transactions. This project demonstrates industry-standard backend practices, focusing on financial precision, data integrity, and clean architecture.

## ✨ Key Features (Implemented in Session 1)
* **Financial Precision:** Uses `BigDecimal` for all monetary calculations to avoid floating-point errors (common with `Double`).
* **Transactional Integrity:** Implements `@Transactional` to ensure atomicity during money transfers—either the whole transfer succeeds, or no changes are made (rollback).
* **Global Exception Handling:** Uses `@RestControllerAdvice` to intercept errors (e.g., "Insufficient funds") and return consistent, user-friendly JSON responses.
* **Layered Architecture:** Clear separation between Controllers, Services, Repositories, and Data Transfer Objects (DTOs).

## 🛠️ Technologies Used
* **Java 17+**
* **Spring Boot 3.x**
* **Spring Data JPA / Hibernate** (ORM & Database access)
* **PostgreSQL** (Production-ready relational database)
* **Jakarta Validation** (Server-side input validation)
* **Maven** (Dependency management)

---

## 🚀 API Endpoints

### 1. Add a New User
Creates a user and automatically generates a bank account with a unique `EE` IBAN and a starting balance of `0.00`.

* **URL:** `POST /api/user/add`
* **Body:**
```json
{
  "name": "Henri Allevi",
  "email": "henri@pank.ee"
}