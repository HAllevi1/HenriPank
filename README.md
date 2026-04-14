# 🏦 HenriPank API

HenriPank is a robust REST API built with **Spring Boot 3** for managing banking users and secure financial transactions. This project demonstrates industry-standard backend practices, focusing on financial precision, data integrity, and clean architecture.

## ✨ Key Features

### 🛡️ Advanced Security (Session 3 - NEW)
* **JWT Authentication:** Implements stateless authentication using JSON Web Tokens. Users receive a "digital wristband" (token) upon login.
* **Password Hashing:** Uses **BCrypt** with a high cost factor to ensure user passwords are never stored in plain text.
* **Stateless Security Filter:** A custom `JwtAuthenticationFilter` intercepts every request to validate tokens before they reach the controller.
* **Secure Access Control:** Granular permission management where registration and login are public, but financial data is strictly protected.

### 📜 Audit Trail & Integrity
* **Double-Entry Ledger:** Every transfer automatically generates two transaction records (Debit/Credit) for a full audit trail.
* **Atomic Transfers:** Implements `@Transactional` to ensure that money transfers are all-or-nothing operations.
* **Financial Precision:** Uses `BigDecimal` for all monetary calculations to eliminate floating-point errors.
* **Data Privacy (DTOs):** Uses Data Transfer Objects to separate internal database logic from the public API.

## 🛠️ Technologies Used
* **Java 17+**
* **Spring Boot 3.x**
* **Spring Security** (Authentication & Authorization)
* **JJWT** (JSON Web Token library)
* **Spring Data JPA / Hibernate**
* **PostgreSQL / H2**
* **Maven**

---

## 🚀 API Endpoints

### 1. Authentication & Access
| Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/user/add` | Register a new user and bank account. | No |
| `POST` | `/api/login` | Authenticate and receive a JWT Token. | No |

### 2. Banking Operations (JWT Required)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/user/accountinfo/{iban}` | View account balance and details. |
| `POST` | `/api/transfer` | Execute a secure money transfer. |
| `GET` | `/api/user/transactions/{iban}` | View full transaction history. |

---

## 📬 Example Workflow

### 1. Login to get Token
**POST** `/api/login`
```json
{
  "email": "henri@pank.ee",
  "password": "securePassword123"
}