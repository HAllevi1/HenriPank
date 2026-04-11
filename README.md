#  HenriPank API

HenriPank is a robust REST API built with **Spring Boot 3** for managing banking users and secure financial transactions. This project demonstrates industry-standard backend practices, focusing on financial precision, data integrity, and clean architecture.

##  Key Features

###  Financial Integrity 
* **Precision Handling:** Uses `BigDecimal` for all monetary calculations to avoid floating point errors.
* **Atomic Transfers:** Implements `@Transactional` to ensure that money never "disappears" during a transfer. If any part of the process fails, the database rolls back to its original state.
* **Global Exception Handling:** Uses `@RestControllerAdvice` to provide clean, consistent JSON error messages (e.g., "Insufficient funds").

###  Audit Trail & Security
* **Double-Entry Ledger:** Every transfer automatically generates two transaction records (Debit for sender, Credit for receiver) for a full audit trail.
* **Relational Mapping:** Leverages JPA `@OneToMany` and `@ManyToOne` relationships to link transactions directly to accounts.
* **Data Privacy (DTOs):** Uses Data Transfer Objects (`TransactionDTO`, `AccountDTO`) to prevent infinite recursion and to hide sensitive internal database IDs from the client.
* **Clean Documentation:** All core service methods are documented using standard Javadoc for better maintainability.

## ️ Technologies Used
* **Java 17+**
* **Spring Boot 3.x**
* **Spring Data JPA / Hibernate**
* **PostgreSQL / H2** (Database)
* **Jakarta Validation**
* **Maven**

---

##  API Endpoints

### 1. User & Account Management
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/user/add` | Creates a user and auto-generates a bank account (EE IBAN). |
| `GET` | `/api/user/{id}` | Retrieves user profile by database ID. |
| `GET` | `/api/user/accountinfo/{iban}` | Returns account balance and IBAN via `AccountDTO`. |

### 2. Banking Operations
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/transfer` | Executes a secure transfer between two accounts with a description. |
| `GET` | `/api/user/transactions/{iban}` | Returns the full transaction history for a specific account. |

---

##  Example Requests

### Money Transfer
**POST** `/api/transfer`
```json
{
  "fromIban": "EE602753",
  "toIban": "EE945644",
  "amount": 20.00,
  "description": "Lõunasöök"
}