# HenriPank API

I built this project to practice backend development beyond basic CRUD apps. The goal was to simulate a simple banking system where users can register, log in, and transfer money between accounts while handling things like transactions, security, and data consistency properly.

---

## What this does

The API handles:

- User registration and login
- Account creation with a generated IBAN
- Viewing account details and transaction history
- Transferring money between accounts

Nothing fancy on the surface, but under the hood I focused on getting the important backend details right.

---

## Tech stack

- Java 17
- Spring Boot
- Spring Security
- JPA / Hibernate
- JWT (JJWT library)
- H2 / PostgreSQL
- Maven

---

## Authentication

Authentication was honestly the hardest part of this project.

I used JWT because I didn’t want to deal with server-side sessions. The flow is:

- User logs in with email and password
- If valid, the server returns a JWT
- Every request after that must include:
  ```
  Authorization: Bearer <token>
  ```
- A custom `JwtAuthenticationFilter` checks the token before the request reaches the controller

Passwords are hashed using BCrypt.

---

## Transfers and money handling

This is the part I cared about the most.

- Transfers are handled inside a `@Transactional` method
- If anything fails (like insufficient balance), the whole operation rolls back
- Each transfer creates:
    - a DEBIT transaction for the sender
    - a CREDIT transaction for the receiver

I used `BigDecimal` for all money values because floating-point errors in financial data are not acceptable.

---

## API overview

### Auth and users

- `POST /api/user/add` → register user + create account
- `POST /api/login` → get JWT token
- `GET /api/user/{id}` → get user info (requires auth)

### Banking

- `GET /api/user/account/{iban}` → account details
- `GET /api/user/transactions/{iban}` → transaction history
- `POST /api/transfer` → transfer money

---

## Example request

Transfer money:

```json
{
  "fromIban": "EE12345",
  "toIban": "EE67890",
  "amount": 150.50,
  "description": "Rent"
}
```

---

## Error handling

Errors are handled globally and return something like:

```json
{
  "status": 404,
  "message": "User not found",
  "timestamp": "2023-10-27T10:00:00"
}
```

---

## Challenges

This wasn’t smooth to build.

- **Spring Security + JWT**  
  Took the most time. Getting the filter, authentication manager, and security context working together was confusing at first. I followed guides, then rewrote parts until I understood the flow.

- **Circular dependencies**  
  Hit this when services started depending on each other. Fixed it by separating responsibilities better and using DTOs instead of passing entities around.

- **Transaction logic**  
  Making sure transfers were safe required thinking through edge cases:
    - what happens if balance is too low
    - what if one side saves and the other fails  
      `@Transactional` solved most of it, but I had to understand why.

---

## Why I did it this way

- **JWT over sessions**  
  I didn’t want server-side session management. JWT keeps things stateless and simpler to scale.

- **Double-entry transactions**  
  It’s closer to how real systems work and makes debugging easier since every transfer is recorded from both sides.

- **Layered structure (Controller → Service → Repository)**  
  Keeps things readable and stops controllers from becoming a mess.

---

## Running the project

1. Clone the repo
2. Run:
   ```
   mvn spring-boot:run
   ```
3. App starts on:
   ```
   http://localhost:8080
   ```

Use Postman or curl to test endpoints.

---

## Things I would improve

- Add refresh tokens
- Add proper tests (unit + integration)
- Dockerize the app
- Add roles (admin/user)
- Rate limiting for endpoints

---

## Final note

This project was mainly about understanding how backend systems actually behave — especially around security and transactions — not just getting endpoints to work.