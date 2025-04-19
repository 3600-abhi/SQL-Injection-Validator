# 🛡️ SQL Injection Validator – Spring Boot AOP Based Library

A reusable and configurable Spring Boot library to automatically detect SQL Injection attempts in incoming API payloads using **Spring AOP** and **Java Reflection** — with **zero code changes required in your controllers**.

---

## 🔍 Why this Library?

In enterprise environments with 100+ microservices and hundreds of APIs, validating SQL injection in every controller is:

- ❌ Repetitive  
- ❌ Error-prone  
- ❌ Hard to scale  

This library solves that by applying **Aspect-Oriented Programming (AOP)** to intercept incoming request payloads and **recursively validate fields for SQL injection** patterns.

---

## ✅ Features

- 🔁 Deep field validation (recursively checks nested DTOs)
- 🧼 Zero controller changes required
- ⚙️ Fully config-driven (enable/disable via `application.properties`)
- 💥 Throws `SqlInjectionException` with proper status
- 🔒 Helps standardize security practices across large systems

---

## 🧠 How It Works

- Aspect class `SqlInjectionValidator` intercepts every method of a `@RestController`
- Checks all parameters annotated with `@RequestBody`
- Uses Java Reflection to recursively scan all fields
- Applies regex to detect SQL keywords like `SELECT`, `DROP`, `--`, `UNION`, etc.
- If match is found, throws `SqlInjectionException` with `400 Bad Request`

---

## ⚙️ Configuration

Enable validation via `application.properties`:

```properties
custom.sql_injection.validate=true
custom.sql_injection.pattern=(?i)(\\b(SELECT|INSERT|UPDATE|DELETE|DROP|CREATE|ALTER|TRUNCATE|EXEC|UNION|JOIN|--|;|/\\*|\\*/|xp_)\\b)
