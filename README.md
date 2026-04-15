🛒 E-Commerce Backend (Spring Boot)

A scalable backend system for an e-commerce platform built using Spring Boot.
It provides REST APIs for managing users, products, orders, and authentication.

---

🚀 Features

- 👤 User registration & authentication (JWT-based)
- 📦 Product management (CRUD operations)
- 🛍️ Order creation & tracking
- 🧾 Cart functionality
- 🔐 Secure API endpoints with Spring Security
- 📄 API documentation using Swagger UI

---

🛠️ Tech Stack

- Backend: Spring Boot, Spring MVC
- Security: Spring Security, JWT
- Database: PostgreSQL / MySQL
- ORM: Hibernate (JPA)
- Build Tool: Maven
- API Docs: Swagger (OpenAPI)

---

📂 Project Structure

src/
 ├── controller/
 ├── service/
 ├── repository/
 ├── model/
 └── config/

---

⚙️ Setup & Run Locally

1. Clone the repository

git clone https://github.com/your-username/your-repo-name.git
cd your-repo-name

2. Configure database

Update "application.properties":

spring.datasource.url=your_db_url
spring.datasource.username=your_username
spring.datasource.password=your_password

3. Run the application

mvn spring-boot:run

---

🌐 API Documentation (Swagger)

After running the app, access:

http://localhost:8080/swagger-ui/index.html

👉 All APIs can be tested directly from Swagger UI.

---

🔗 Live Demo

«⚠️ The deployed instance is currently being redeployed and will be available soon.»

(Will be updated with live link)

---

📌 Sample API Endpoints

Method| Endpoint| Description
POST| /auth/register| Register user
POST| /auth/login| Login user
GET| /products| Get all products
POST| /orders| Create order
GET| /orders/{id}| Get order details

---

🔐 Authentication

- Uses JWT-based authentication
- Include token in header:

Authorization: Bearer <your-token>

---

🧠 Key Learnings

- Built RESTful APIs with proper layering
- Implemented authentication & authorization
- Handled database relationships using JPA
- Designed scalable backend architecture

---

📬 Contact

For any queries or discussion, feel free to reach out.

---
