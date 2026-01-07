
---

## 👩‍💻 Author

**Garima Singh**  

---

## 📌 Notes

This project was developed as part of an internship assignment. The focus was on correct business logic, clean structure, and real-world backend practices.
# Personal Finance Manager – Backend API

A Spring Boot–based backend application for managing personal finances, developed as part of a **Backend Intern Assignment**.

The application supports user authentication, transaction management, financial goals, and report generation with validations and exception handling.

---

## 🚀 Tech Stack

- Java 17
- Spring Boot
- Spring Security (JWT Authentication)
- Spring Data JPA (Hibernate)
- H2 Database (production)
- Maven
- Deployed on Render

---

## ✨ Features Implemented

### 🔐 Authentication & Authorization
- User registration and login
- JWT-based authentication
- Secure access to user-specific resources

### 💰 Transactions
- Add income and expense transactions
- Categorized transactions
- Fetch transaction history

### 🎯 Goals
- Create, update, delete financial goals
- Track progress based on income and expenses
- Business rule validations (dates, ownership, etc.)

### 📊 Reports
- Monthly financial reports
  - Income breakdown by category
  - Expense breakdown by category
  - Net savings calculation
- Input validation for report parameters

### ⚠️ Exception Handling
- Centralized global exception handling
- Custom exceptions for:
  - Validation errors
  - Resource not found
  - Forbidden access
  - Conflict scenarios

---

## 🧪 Testing

- API tested using the provided automated test suite
- **Current test results:**
  - Total Tests: 86
  - Passed: 54
  - Success Rate: 62%

Core scenarios are implemented and verified. Some edge cases (e.g. formatting precision, yearly reports) are partially implemented and can be extended further.

---

## 🌐 Deployed Application

**Base URL:**  https://personal-finance-manager-1-l7rc.onrender.com/
