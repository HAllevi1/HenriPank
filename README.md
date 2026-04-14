# HenriPank API

HenriPank is a REST API built with Spring Boot 3 for managing banking users and secure financial transactions. The project simulates core banking operations while focusing on transactional integrity, security, and clean backend architecture.

---

## Key Features

### Advanced Security
- JWT-based stateless authentication (24h validity)
- Password hashing using BCrypt (`PasswordEncoder`)
- Custom `JwtAuthenticationFilter` for request validation
- Public endpoints for authentication, protected endpoints for all financial operations

---

### Audit Trail and Data Integrity
- Double-entry ledger (debit and credit records per transfer)
- Atomic transactions using `@Transactional`
- Financial precision with `BigDecimal`
- Centralized error handling via `GlobalExceptionHandler`

---

## Technologies Used

- Java 17
- Spring Boot 3.x
- Spring Security
- JJWT
- Spring Data JPA / Hibernate
- H2 / PostgreSQL
- Maven

---

## API Endpoints

### Authentication and User Management

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/user/add` | Register a new user and create an account with a generated IBAN | No |
| POST | `/api/login` | Authenticate user and receive JWT token | No |
| GET | `/api/user/{id}` | Retrieve user profile by ID | Yes |

---

### Banking Operations

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/user/account/{iban}` | Retrieve account details and balance | Yes |
| GET | `/api/user/transactions/{iban}` | Retrieve transaction history | Yes |
| POST | `/api/transfer` | Execute money transfer between accounts | Yes |

---

## Example Workflow

### 1. Create a New User

**POST** `/api/user/add`

```json
{
  "name": "Henri",
  "email": "henri@pank.ee",
  "password": "securePassword123"
}
```

---

### 2. Login

**POST** `/api/login`

```json
{
  "email": "henri@pank.ee",
  "password": "securePassword123"
}
```

Response:
```
<JWT_TOKEN>
```

Use in requests:
```
Authorization: Bearer <JWT_TOKEN>
```

---

### 3. Transfer Money

**POST** `/api/transfer`

Headers:
```
Authorization: Bearer <JWT_TOKEN>
```

Body:
```json
{
  "fromIban": "EE12345",
  "toIban": "EE67890",
  "amount": 150.50,
  "description": "Monthly Rent"
}
```

---

## Business Rules

- Transfers are only executed if the sender has sufficient balance
- Both sender and receiver accounts must exist
- Each transfer creates:
    - One debit transaction
    - One credit transaction
- All transfers are atomic (either fully succeed or fail)

---

## Data Model

Core entities:

- **User**
- **Account**
- **Transaction**

Each transaction includes:
- amount (`BigDecimal`)
- Two records
- associated account
- timestamp
- optional description

---

## Error Handling

All errors return a consistent structure:

```json
{
  "status": 404,
  "message": "User not found",
  "timestamp": "2023-10-27T10:00:00"
}
```

### Common Status Codes

| Code | Meaning |
|------|--------|
| 401 | Unauthorized |
| 404 | Resource not found |
| 500 | Internal server error |

---

## Running the Project

1. Clone the repository
2. Run:
   ```
   mvn spring-boot:run
   ```
3. Application runs at:
   ```
   http://localhost:8080
   ```
4. Use Postman or curl to test endpoints

---

## Architecture Overview

- Layered architecture:
    - Controller → Service → Repository
- DTO pattern for request and response separation
- Centralized exception handling
- Stateless authentication design

---

## Future Improvements

- Role-based access control (admin/user roles)
- Refresh token implementation
- Integration and unit testing
- Docker containerization
- Rate limiting for security

---

## License

This project is for educational and portfolio purposes.