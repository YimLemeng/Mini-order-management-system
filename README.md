# 🛒 Mini Order Management System (Backend API)

A robust, enterprise-grade RESTful API for an **Order Management System** built with **Spring Boot 3**, **Java 21**, and **PostgreSQL**. The project follows Clean Layered Architecture, JPA Relational Modeling, Transactional Inventory Management, and Containerized Docker Deployment.

---

## 🛠️ Tech Stack

- **Java**: 21 (Eclipse Temurin)
- **Framework**: Spring Boot 3.x
- **Database**: PostgreSQL 16
- **ORM / Persistence**: Spring Data JPA / Hibernate
- **Build Tool**: Maven (`mvnw`)
- **Containerization**: Docker & Docker Compose
- **Deployment**: Render.com (Backend & Managed PostgreSQL) + Cloudflare (Frontend)

---

## 🚀 Key Features

- **Clean Layered Architecture**: Decoupled Controllers, Service Interfaces & Implementations, Mappers, Repositories, and Entities.
- **Request/Response DTO Pattern**: Encapsulated data transfer objects (`CustomerRequest`, `CustomerResponse`, `OrderRequest`, `OrderResponse`, etc.).
- **JPA Relational Mappings**:
  - `@OneToOne`: Customer ↔ CustomerProfile
  - `@OneToMany` / `@ManyToOne`: Customer ↔ Order ↔ OrderItem
  - `@ManyToMany`: Product ↔ Category
- **Transactional Stock & Inventory Control**:
  - Automatically validates available product stock before creating an order.
  - Automatically depletes stock quantities and calculates total price under `@Transactional`.
  - Automatically restocks inventory if an order status is updated to `CANCELLED`.
- **Global Exception Handling**: Centralized `@ControllerAdvice` handling validation errors (`@Valid`) and custom exceptions (`ResourceNotFoundException`, `InsufficientStockException`, `ResourceAlreadyExistsException`).
- **Global CORS Config**: Configured to connect seamlessly with React JS, Vue, or Angular frontends.

---

## ⚙️ Getting Started

### 1. Prerequisites

- **Java 21+** installed
- **PostgreSQL 15+** installed locally or running via Docker

---

### 2. Environment Configuration

Copy the template `.env.example` file to `.env`:

```bash
cp .env.example .env
```

Update your database credentials in `.env`:

```properties
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/order_system
SPRING_DATASOURCE_USERNAME=order_system
SPRING_DATASOURCE_PASSWORD=your_password_here
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SERVER_PORT=8080
```

---

### 3. Run Locally (Maven)

```bash
./mvnw clean spring-boot:run
```

---

### 4. Run with Docker Compose

Run the entire stack (PostgreSQL Database + Spring Boot Backend) using Docker:

```bash
docker compose up --build -d
```

To stop the containers:

```bash
docker compose down
```

---

## 📡 API Endpoints Overview

### 👥 Customers (`/api/customers`)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/customers` | Get all customers (supports search & pagination) |
| `GET` | `/api/customers/{id}` | Get customer details by ID |
| `POST` | `/api/customers` | Register a new customer |
| `PUT` | `/api/customers/{id}` | Update customer & profile |
| `DELETE` | `/api/customers/{id}` | Delete customer |

### 📦 Products (`/api/products`)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/products` | Get all products & stock status |
| `GET` | `/api/products/{id}` | Get product details by ID |
| `POST` | `/api/products` | Create a new product |
| `PUT` | `/api/products/{id}` | Update product & stock quantity |
| `DELETE` | `/api/products/{id}` | Delete product |

### 🛍️ Orders (`/api/orders`)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/orders` | Get all orders & transaction history |
| `GET` | `/api/orders/{id}` | Get order details with items |
| `POST` | `/api/orders` | Place a new order (auto stock deduction) |
| `PATCH` | `/api/orders/{id}/status` | Update order status (auto restock if CANCELLED) |
| `DELETE` | `/api/orders/{id}` | Delete order |

---

## 📂 Project Structure

```text
src/main/java/com/Ordermanagement/OrderManagementSystem/
├── config/             # Global CORS & Configuration
├── Controller/         # REST API Controllers
├── DTO/                # Request & Response DTOs
├── Entity/             # JPA Database Entities & Enums
├── Exception/          # Custom Exceptions & Global Handler
├── Mapper/             # Entity <-> DTO Mapping Layer
├── Repository/         # Spring Data JPA Repositories
└── Service/            # Business Logic & Service Implementations
```

---

## 🌐 Live Production Links

- **Backend API**: [https://mini-order-management-system.onrender.com](https://mini-order-management-system.onrender.com)
- **Frontend Dashboard**: [https://mini-order-management-web.yimlemeng069.workers.dev](https://mini-order-management-web.yimlemeng069.workers.dev)

---

## 📄 License
This project is open-source and available under the [MIT License](LICENSE).
