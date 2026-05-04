# WM Planner Backend

This repository contains the backend for the **WM Planner**:
👉 https://github.com/maxmp-q/wm-planner

The backend provides a REST API consumed by the frontend and is responsible for:

* Authentication (JWT-based)
* Access to Firestore
* Providing application data (users, cards, etc.)

---

## 🛠️ Tech Stack

* Java 25
* Spring Boot
* Spring Security
* JWT (Authentication)
* Firebase Admin SDK (Firestore)
* Docker
* Deployment via Render

---

## 🚀 Deployment

The backend is deployed on **Render**.

A `Dockerfile` is included, so the application can also be easily containerized and deployed anywhere.

---

## 🔐 Environment Variables

Two environment variables are required to run the application:

### 1. `JWT_SECRET`

Secret key used to sign and validate JWT tokens.

Example:

```
JWT_SECRET=your-super-secure-secret-key-with-min-32-bytes
```

⚠️ Important:

* Must be sufficiently long (at least 32 bytes)
* Used for both token creation and validation

---

### 2. `FIREBASE_CONFIG` (or Service Account setup)

Used to connect to Firebase / Firestore.

Depending on your setup:

* Provide a Service Account JSON
* Or configure credentials via your hosting provider (e.g. Render)

---

## 🧪 Local Development

### Requirements

* Java 25
* Maven

### Run the application

```
mvn spring-boot:run
```

Or start it directly via your IDE (e.g. IntelliJ).

---

## 🐳 Docker

A `Dockerfile` is included.

### Build:

```
docker build -t wm-planner-backend .
```

### Run:

```
docker run -p 8080:8080 \
  -e JWT_SECRET=your-secret \
  -e FIREBASE_CONFIG=your-config \
  wm-planner-backend
```

---

## 🔑 Authentication

The backend uses **JWT-based authentication**.

### Login

```
POST /auth/login
```

Response:

```json
{
  "token": "..."
}
```

### Usage

For all protected endpoints, include the token in the request header:

```
Authorization: Bearer <token>
```

---

## 🔒 Security

* All endpoints except `/auth/login` are protected
* Tokens are validated server-side using Spring Security and a custom filter
* Firestore is accessed exclusively through the backend
* Security Rules in Firestore can be set to fully restrictive (`allow false`)

---

## 📡 API Overview

| Endpoint      | Description           |
| ------------- | --------------------- |
| `/auth/login` | Login & receive token |
| `/users`      | Get users             |
| `/cards`      | Get cards             |
| `/heading`    | Get heading           |

---

## 🧠 Architecture

* **Controller** → REST endpoints
* **Service Layer** → business logic
* **Firebase Admin SDK** → database access
* **JWT Filter** → request authentication

---

## 🔗 Frontend

The corresponding frontend can be found here:
👉 https://github.com/maxmp-q/wm-planner

---

## 📌 Notes

* This backend is intentionally simple (single-user / password-based login)
* Focus is on providing a secure and lightweight API
* No direct Firebase usage from the frontend is required

---

## 📄 License


MIT License


---

## 👤 Author

maxmp-q
GitHub: https://github.com/maxmp-q
