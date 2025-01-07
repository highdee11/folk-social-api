# FolkSocial API

FolkSocial is a robust backend API designed to power a modern social media platform. The API facilitates user authentication, social interactions, and secure data management, enabling seamless integration for frontend applications.

**Note:** This project is currently a work in progress. Features and endpoints are being actively developed.

## Features

- **User Authentication**: Secure user registration and login using JWT authentication.
- **Role-Based Access Control**: Ensure restricted access to specific resources.
- **Scalable Design**: Built with Spring Boot for scalability and performance.
- **Error Handling**: Comprehensive error messages and HTTP status codes.
- **Stateless API**: Implements a stateless design using JWT.

---

## Table of Contents

1. [Installation](#installation)
2. [Usage](#usage)
3. [API Endpoints](#api-endpoints)
4. [Technologies](#technologies)
5. [Contributing](#contributing)
6. [License](#license)

---

## Installation

### Prerequisites

- Java 17 or later
- Maven 3.8 or later
- MySQL (or any configured database)

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repository/folksocial-api.git
   cd folksocial-api
   ```

2. Configure the database:
    - An example application.properties file has been added.
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/folksocial
       username: YOUR_DB_USERNAME
       password: YOUR_DB_PASSWORD
     jpa:
       hibernate:
         ddl-auto: update
   ```

3. Build the project:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

---

## Usage

### Base URL

The base URL for all endpoints is:
```
http://localhost:8080/api
```

### Authentication
1. **Register**:
    - Endpoint: `POST /auth/register`
    - Body:
      ```json
      {
        "email": "highdee.ai+8@gmail.com",
        "firstname": "Idowu",
        "lastname": "Aladesiun",
        "password": "secret",
        "dob": "22-07-1996"
       }
      ```
    - Response:
      ```json
      {
        "code": "SUC001",
        "message": "Request was successful",
        "data": null
      }
      ```

2. **Login**:
    - Endpoint: `POST /auth/login`
    - Body:
    ```json
      {
        "email": "user@example.com",
        "password": "securepassword"
      }
    ```

    - Response:
    ```json
      {
        "code": "SUC001",
        "message": "Request was successful",
        "data": {
          "id": "12",
          "email": "highdee.ai@gmail.com",
          "firstname": "Idowu",
          "lastname": "Aladesiun",
          "token": "Auth JWT token"
        }
      }
   ```

3. **Create Post**:
   - Endpoint: `POST /post`
   - Body:
   ```json
   {
     "content": "Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum",
     "media": [
       {
         "type": "IMAGE",
         "url": "https://media.istockphoto.com/id/1458782106/photo/scenic-aerial-view-of-the-mountain-landscape-with-a-forest-and-the-crystal-blue-river-in.jpg?s=2048x2048&w=is&k=20&c=jbXMS_yFujUo29EIjPd8XBsEan-PAHUcPs0Zo1-HY_U="
       }
     ]
   }
   ```
   - Response:
   ```json
     {
      "code": "SUC001",
      "message": "Request was successful",
      "data": {
         "id": 1,
         "userId": 1,
         "content": "Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum",
         "createdAt": "2025-01-06T21:11:51.724853"
       }
     }
   ```

---

## API Endpoints

| Method | Endpoint               | Description                     |
|--------|------------------------|---------------------------------|
| POST   | `/auth/register`       | Register a new user             |
| POST   | `/auth/login`          | Authenticate user               |

---

## Technologies

- **Java 17**: Core programming language.
- **Spring Boot**: Framework for building the API.
- **Spring Security**: Handles authentication and authorization.
- **JWT**: Secure stateless authentication.
- **MySQL**: Relational database for data persistence.
- **Maven**: Build and dependency management.
- **JUnit 5**: Testing framework.
- **Mockito**: Mocking library for unit tests.

---

## Contributing

Contributions are welcome! Please fork this repository and submit a pull request.

### Steps to Contribute:
1. Fork the project.
2. Create a feature branch (`git checkout -b feature-name`).
3. Commit your changes (`git commit -m 'Add feature'`).
4. Push to the branch (`git push origin feature-name`).
5. Open a pull request.

---

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

---

## Future Enhancements

- Add more user roles (e.g., admin, moderator).
- Implement advanced search and filtering for posts.
- Add file upload capabilities for images and videos.
- Enhance real-time capabilities with WebSocket integration.

