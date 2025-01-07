Spring Boot CRUD API with Authentication and Validation
-------------
This project demonstrates how to develop a RESTful API using Spring Boot that performs CRUD operations on a Product
resource. It includes authentication, validation, global error handling, and Swagger-based documentation. The API
interacts with a PostgreSQL database for persistence.


Table of Contents
-----

- Project Setup
- Technologies Used
- Building and Running
- Endpoints
- Unit Tests

Project Setup
--
Follow the steps below to set up the project

Prerequisites
--
Ensure that you have the following tools installed:

- Java 21
- Maven
- Postgresql

Clone the Repository
---
Clone the repository to your local machine.
```
git clone https://github.com/bhupendrabirla/product-management.git
cd product-management
```

Build the Project
---
```mvn clean install```

Set up the Database
---

1. Configure your database connection in the application.yml file located in src/main/resources

```
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/product-management
    username: postgres
    password: postgres
```

2. Create the database ```product-management``` if it doesn't already exist.

Run the Application
----
To run the application, use the following command:

```mvn spring-boot:run```

Technologies Used
---

- Spring Boot: Framework for building the RESTful API.
- Spring Data JPA: For persistence with relational databases.
- MySQL or PostgreSQL: Relational database for data persistence.
- Spring Security: For authentication and authorization.
- Swagger/OpenAPI: For API documentation.
- Hibernate Validator: For input validation.
- Maven or Gradle: Build automation tools.

Endpoints
--
1. Open the swagger-ui with url [swagger-document](http://localhost:8080/swagger-ui/index.html)
2. **Authentication**
   - For Authentication you need first signup with a user using `/auth/signup` endpoint with providing requested user details.
   - Once you have signed up with a user. you can now login to the application using `/auth/login` endpoint with the registered user.
   - The login api return LoginResponse with accessToken field in the response body.
   - Copy the value of the accessToken and paste into the top right corner of the swagger-ui with Authorize button
   - Now you can access the rest of the endpoints for managing products.
3. **Product CRUD Operations**

   - GET /products Retrieves all products list.
   - GET /products/{id} Retrieves a product by ID.
   - POST /products Creates a new product.
   - PUT /products/{id} Updates an existing product by ID.
   - DELETE /products/{id} Deletes a product by ID.

Unit Tests
---
Tests cases ensure the correctness and reliability of the application

Application covers Unit tests cases focusing individual components of the application (such as the service layer) in isolation which ensure that each business logic component works as expected.






