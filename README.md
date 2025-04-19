# 🛡️ SQL Injection Validator – Spring Boot AOP Based Library

A lightweight plug-n-play library built using **Spring AOP + Java Reflection** to **automatically detect SQL injection attempts** in incoming API payloads — without writing repetitive validation code in controllers.

---

## 🚀 Why this Library?

In large-scale applications with 100+ microservices and hundreds of APIs, writing SQL injection checks manually in every controller becomes:

- ❌ Repetitive
- ❌ Hard to maintain
- ❌ Prone to human error

This library solves that by introducing a **centralized validation mechanism** using Aspect-Oriented Programming.

---

## ⚙️ Features

✅ Intercepts all `@RestController` API methods  
✅ Scans `@RequestBody` fields (including nested objects)  
✅ Uses configurable regex to detect SQL injection patterns  
✅ Throws custom exception on detection  
✅ Toggle validation ON/OFF using application properties  
✅ No changes required in controller/service layers

---

## 📦 How It Works

- An AOP aspect runs before each controller method.
- It inspects all method parameters with `@RequestBody`.
- Using Java reflection, it recursively reads all fields in the object.
- Regex pattern is applied to detect SQL keywords like `SELECT`, `DROP`, `--`, `' OR 1=1`, etc.
- If a match is found, a custom `RESTException` is thrown.

---

## 🔧 Configuration

Add the following to your `application.properties`:

```properties
# Enable or disable validation
custom.sql_injection.validate=true

# Override default regex (optional)
custom.sql_injection.pattern=(?i)(\\b(SELECT|INSERT|UPDATE|DELETE|DROP|CREATE|ALTER|TRUNCATE|EXEC|UNION|JOIN|--|;|/\\*|\\*/|xp_)\\b)
