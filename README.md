
![Spring Boot](https://img.shields.io/badge/Built%20With-Spring%20Boot-brightgreen)
![Java](https://img.shields.io/badge/Java-17+-orange)
![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)
![Status](https://img.shields.io/badge/Status-Learning%20Project-yellow)
![Made with Love](https://img.shields.io/badge/Made%20with-%E2%9D%A4-red)

# Spring Boot Marketplace Project

This is a basic project built using **Spring Boot** and **Core Java**, created with the primary goal of learning Spring Boot fundamentals.

## Features

- **User Authentication**  
  Users can log in with existing accounts or create new accounts.

- **Role-Based Actions**  
  - Accounts with the role of **Seller** can list items for sale (upload a photo, enter item name, and provide details).
  - Accounts with the role of **Buyer** can browse and purchase listed items.

- **Profile Management**  
  Every user can update their profile information, including first name, last name, and email address.

- **Search Functionality**  
  A search box in the navigation bar allows users to quickly find items.

- **Custom Error Pages**  
  User-friendly error pages are displayed when an invalid endpoint is accessed.

- **Database Design**  
  The application uses **five database tables** to efficiently organize and manage the data.

## Technologies Used

- **Spring Boot** — Main framework for building the backend.
- **Spring Security** — Handles authentication, authorization, and role-based access control.
- **Spring Data JPA** — For database interaction using Java Persistence API (JPA).
- **MySQL Database** — To store user data, items, and other application information.
- **MySQL Workbench** — For managing and visualizing the MySQL database.
- **Thymeleaf** — Server-side Java template engine for rendering HTML views.
- **Lombok** — Reduces boilerplate code by generating getters, setters, constructors, etc.
- **Spring Boot DevTools** — Provides hot-reloading and development tools to improve productivity.
- **Cloudinary API** — Used for uploading and storing item photos and user profile pictures.
- **HTML/CSS (Basic)** — For frontend structure and simple styling.

## Project Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/your-repo-name.git
   ```
2. **Set up the database**
   - Create a new MySQL database.
   - Update `application.properties` (or `application.yml`) with your database credentials.

3. **Configure Cloudinary (optional)**
   - Create a Cloudinary account.
   - Add your Cloudinary API details in the project config files.

4. **Build and run the project**
   ```bash
   ./mvnw spring-boot:run
   ```
   or if using Maven directly:
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application**
   - Open your browser and go to `http://localhost:8080`.

> Make sure you have Java 17+ and Maven installed before running the project.

## Resources

- [Get Current User’s Extra Information Using Thymeleaf sec Tag (StackOverflow)](https://stackoverflow.com/questions/45546327/get-current-users-extra-information-by-thymeleaf-sec-tag-working-with-spring-se)
- [Fix CSS Not Loading with Spring Security and Thymeleaf (StackOverflow)](https://stackoverflow.com/questions/54848419/cant-load-my-css-when-using-spring-security-and-thymeleaf)
- [Spring Boot + Security + JPA Full Tutorial (YouTube)](https://www.youtube.com/watch?v=YL5x8M7ludE)
- [Cloudinary Documentation](https://cloudinary.com/documentation)

## Notes

- This project is intended for **learning purposes** and focuses on understanding:
  - Spring Boot fundamentals
  - JPA (Java Persistence API)
  - Spring Security
  - Custom error handling
  - Cloudinary integration for image upload

- The code prioritizes **clarity and learning** over production-level optimization.

---

> Feel free to explore and improve upon it

