# Authentication and Role-Based Access Control (RBAC) System

## **Project Overview**

This project implements an authentication and authorization system with Role-Based Access Control (RBAC) using **Spring Boot** and **MySQL**. It includes user registration, login functionality, and role-based access for various resources. The authentication is handled using **JWT** (JSON Web Tokens), and the application supports different roles such as `USER` and `ADMIN`.

## **Key Features**

- **User Registration**: New users can register by providing a username and password. Default roles are assigned (`USER` by default, `ADMIN` if specified).
- **User Login**: Registered users can log in using their credentials, and upon successful login, a JWT token is generated for subsequent requests.
- **Role-Based Access Control (RBAC)**: The system restricts access to certain endpoints based on the user's role.
  - `ADMIN` role can access `/admin/**` endpoints.
  - `USER` role can access `/user/**` endpoints.

## **Technologies Used**

- **Backend**: Spring Boot (Java 21)
- **Database**: MySQL
- **Authentication**: JWT (JSON Web Tokens)
- **Dependency Management**: Maven
- **Role-Based Access Control**: Implemented using Spring Security

## **Requirements**

- JDK 21 or above
- MySQL 8 or above
- Maven 3.6 or above

## **Setup Instructions**

### 1. **Clone the Repository**

```bash
git clone https://github.com/SatwikAkundi/VRVAssignment.git
```
## **Project Setup Instructions**

### 2. **Database Configuration**
Make sure you have MySQL installed and running. Create a new database:
```sql
CREATE DATABASE your_database_name;
```
Then, update the database configuration in the src/main/resources/application.properties file with your database details:
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=root
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```
### 3. **Add Dependencies**
Add the following dependencies in your pom.xml file:
```xml
<dependencies>
    <!-- Spring Boot Starter Web for building REST APIs -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!-- Spring Boot Starter Security for authentication and authorization -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <!-- Spring Boot Starter Data JPA for database interactions -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <!-- MySQL Driver -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    <!-- JSON Web Token (JWT) library -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt</artifactId>
        <version>0.11.5</version>
    </dependency>
</dependencies>
```

### 4. **Run the Application**
Run the Spring Boot application from the command line:
```bash
mvn spring-boot:run
```
### 5. **Postman Setup**
Once the application is running, you can test the authentication using Postman:

**POST /register** - Register a new user.

**POST /login** - Login using the registered username and password to receive a JWT.

**GET /admin/**** - Access the admin section (requires ADMIN role).

**GET /user/**** - Access the user section (requires USER role).

### 6. **Test Example**
**Register User (POST /register)**
```json
{
    "username": "john_doe",
    "password": "password123",
    "role": "USER"
}
```
**Login User (POST /login)**
```json
{
    "username": "john_doe",
    "password": "password123"
}
```
**Access Admin Endpoint (GET /admin)**

**Headers:**

-Authorization: Bearer <JWT_TOKEN>

**Access User Endpoint (GET /user)**

**Headers:**

-Authorization: Bearer <JWT_TOKEN>

**File Structure**
```css
src/
 ├── main/
 │    ├── java/
 │    │    ├── com/
 │    │    │    ├── vrv/
 │    │    │    │    ├── vrvsecurityassignment/
 │    │    │    │    │    ├── Controller/
 │    │    │    │    │    ├── Model/
 │    │    │    │    │    ├── Repository/
 │    │    │    │    │    ├── Security/
 │    │    │    │    │    ├── Service/
 │    └── resources/
 │         ├── application.properties
```

**Endpoints**

**POST /register** - Registers a new user.

**POST /login** - Logs in a user and returns a JWT token.

**GET /admin/**** - Protected admin endpoints (accessible by ADMIN users only).

**GET /user/**** - Protected user endpoints (accessible by USER users only).

**Known Issues**
JWT expiration time can be adjusted in the JWTService class.
You may need to modify the application properties for connecting to the MySQL database.
Contributing
Feel free to fork this project and create a pull request for any improvements or bug fixes!

License
This project is licensed under the MIT License - see the LICENSE file for details.

```yaml

---

### How to use this README file:

1. Create a `README.md` file in your project’s root directory.
2. Copy and paste the above content into the `README.md` file.
3. Push the file to your GitHub repository.

This file provides setup instructions, usage, and test cases.
```




