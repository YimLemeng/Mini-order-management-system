# 🛒 Mini Order Management System (Backend API)

A production-ready Enterprise REST API for an **Order Management System** built with **Spring Boot 3**, **Java 17**, and **PostgreSQL**, following Clean Layered Architecture and industry best practices.

---

## 🚀 Key Features

- **Clean Layered Architecture**: Decoupled Controllers, Service Interfaces & Implementations, Mappers, Repositories, and Entities.
- **Request/Response DTO Pattern**: Encapsulated data transport objects (`CustomerRequest`, `CustomerResponse`, `OrderRequest`, `OrderResponse`, etc.).
- **JPA Database Relationships**:
  - `@OneToOne`: Customer ↔ CustomerProfile
  - `@OneToMany` / `@ManyToOne`: Customer ↔ Order ↔ OrderItem
  - `@ManyToMany`: Product ↔ Category
- **Transactional Business Logic & Stock Control**:
  - Automatically verifies available inventory stock before placing an order.
  - Dynamically calculates order total price and depletes stock under `@Transactional`.
  - Automatically restores inventory stock when an order status is updated to `CANCELLED`.
- **Centralized Exception Handling**: Global `@ControllerAdvice` handling custom exceptions (`ResourceNotFoundException`, `InsufficientStockException`, `ResourceAlreadyExistsException`) and input validation errors (`@Valid`).
- **Pagination, Search & Dynamic Sorting**: Built-in support for paginated results (`Pageable`), custom JPQL search queries, and dynamic sorting.
- **CORS Configuration**: Configured to support frontend client integration (React JS / Angular / Vue).

---

## 🛠️ Tech Stack

- **Framework**: Spring Boot 3.3.x
- **Language**: Java 17
- **Database**: PostgreSQL
- **ORM / Persistence**: Spring Data JPA / Hibernate
- **Tooling**: Lombok, Maven

---

## 📂 Project Structure

```text
src/main/java/com/example/ordermanagement/
├── config/             # Global CORS & App Configurations
├── controller/         # REST Controllers (API Endpoints)
├── dto/                # Request & Response Data Transfer Objects
├── entity/             # JPA Database Entities & Enums
├── exception/          # Custom Exceptions & Global Exception Handler
├── mapper/             # Entity <-> DTO Mapping Layer
├── repository/         # Spring Data JPA Repositories
└── service/            # Service Interfaces & Implementations
