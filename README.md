# FolkSocial API 

FolkSocial is a robust backend API designed to power a modern social media platform. The API facilitates user authentication, social interactions, and secure data management, enabling seamless integration for frontend applications.

**Note:** This project is currently a work in progress. Features and endpoints are being actively developed.

## Features

- **User Authentication**: Secure user registration and login using JWT authentication.
- **Posts and Comments**:
  - Create, retrieve, update, and delete posts. 
  - Supports hierarchical comments, allowing users to reply to posts by creating new posts linked as comments. 
  - Pagination for efficient retrieval of large datasets.
- **Scalable Design**: Built with Spring Boot for scalability and performance.
- **Error Handling**: Comprehensive error messages and HTTP status codes.
- **Stateless API**: Implements a stateless design using JWT.

---

## Table of Contents

1. [Installation](#installation)
2. [Usage](#usage)
3. [API Endpoints](#api-endpoints)
4. [Postman Collection](#postman-collection)
5. [Technologies](#technologies)
6. [Contributing](#contributing)

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
#### Note: We have decided to stop adding more endpoints to the readme file, Kindly check the [postman collection](#postman-collection) for more detailed endpoints
### Base URL

The base URL for all endpoints is:
```
http://localhost:8080/api
```

### Authentication
1. **Register**:
    - Endpoint: `POST /api/auth/create-account`
    - Body:
      ```json
      {
        "email": "highdee.ai+8@gmail.com",
        "username": "highdee",
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
    - Endpoint: `POST /api/auth/login`
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
3. **Get User**
    - Endpoint: `GET /api/user`
      - Response: 
        ```
        {
             "code": "SUC001",
             "message": "Request was successful",
             "data": {
                 "id": 1,
                 "email": "highdee.ai+2@gmail.com",
                 "firstname": "Idowu",
                 "lastname": "Aladesiun"
             }
         }
    ```
4. **Create Post**:
      - Endpoint: `POST /api/post`
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
                "id": 13,
                "content": "Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum",
                "author": {
                     "id": 1,
                     "email": "test-user@gmail.com",
                     "firstname": "Test",
                     "lastname": "User"
                  },
                "createdAt": "2025-01-06T21:11:52",
                "media": [
                  {
                    "type": "image",
                    "url": "https://media.istockphoto.com/id/1458782106/photo/scenic-aerial-view-of-the-mountain-landscape-with-a-forest-and-the-crystal-blue-river-in.jpg?s=2048x2048&w=is&k=20&c=jbXMS_yFujUo29EIjPd8XBsEan-PAHUcPs0Zo1-HY_U="
                  }
                ]
             }
           }
         ```

4. **Get Single Post**:
   - Endpoint: `GET /api/post/{id}`
   - Response:
     ```json
        {
           "code": "SUC001",
           "message": "Request was successful",
           "data": {
              "id": 1,
              "content": "Lorem Ipsum",
              "author": {
                 "id": 1,
                 "email": "test-user@gmail.com",
                 "firstname": "Test",
                 "lastname": "User"
              },
              "createdAt": "2025-01-11T12:30:55",
              "media": [
                 {
                 "type": "image",
                 "url": "https://media.istockphoto.com/id/1458782106/photo/scenic-aerial-view-of-the-mountain-landscape-with-a-forest-and-the-crystal-blue-river-in.jpg?s=2048x2048&w=is&k=20&c=jbXMS_yFujUo29EIjPd8XBsEan-PAHUcPs0Zo1-HY_U="
                 }
              ]
          }
       }
     ```
        
5. **List Posts**:
   - Endpoint: `GET /api/posts?page=0&size=2`
     - Response:
     ```json
      {
         "code": "SUC001",
         "message": "Request was successful",
         "data": {
         "content": [
            {
               "id": 1,
               "content": "Lorem Ipsum",
               "author": {
                  "id": 1,
                  "email": "test-user@gmail.com",
                  "firstname": "Test",
                  "lastname": "User"
               },
               "createdAt": "2025-01-11T12:30:55",
               "media": [
                  {
                     "type": "image",
                     "url": "https://media.istockphoto.com/id/1458782106/photo/scenic-aerial-view-of-the-mountain-landscape-with-a-forest-and-the-crystal-blue-river-in.jpg?s=2048x2048&w=is&k=20&c=jbXMS_yFujUo29EIjPd8XBsEan-PAHUcPs0Zo1-HY_U="
                  }
               ]
            },
            {
               "id": 2,
               "content": "Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum",
               "author": {
                  "id": 1,
                  "email": "test-user@gmail.com",
                  "firstname": "Test",
                  "lastname": "User"
               },
               "createdAt": "2025-01-11T13:17:58",
               "media": [
                  {
                  "type": "image",
                  "url": "https://media.istockphoto.com/id/1458782106/photo/scenic-aerial-view-of-the-mountain-landscape-with-a-forest-and-the-crystal-blue-river-in.jpg?s=2048x2048&w=is&k=20&c=jbXMS_yFujUo29EIjPd8XBsEan-PAHUcPs0Zo1-HY_U="
                  }
               ]
            }
         ],
         "pageable": {
            "pageNumber": 0,
            "pageSize": 2,
            "sort": {
               "empty": true,
               "unsorted": true,
               "sorted": false
            },
            "offset": 0,
            "unpaged": false,
            "paged": true
         },
         "last": false,
         "totalPages": 2,
         "totalElements": 4,
         "first": true,
         "size": 2,
         "number": 0,
         "sort": {
            "empty": true,
            "unsorted": true,
            "sorted": false
         },
         "numberOfElements": 2,
         "empty": false
         }
      }
     ```
   
6. **Delete Post**:
      - Endpoint: `DELETE /api/post/{id}`
      - Response:
        ```json
           {
               "code": "SUC001",
               "message": "Request was successful",
               "data": null
           }
        ```
        
7. **Create Comment**:
    - Endpoint: `POST /api/post`
    - Body:
      ```json
        {
          "parent_id": 1,
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
                  "id": 7,
                  "content": "Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum",
                  "author": {
                  "id": 2,
                  "email": "test-user@gmail.com",
                  "firstname": "John",
                  "lastname": "Doe"
              },
              "createdAt": "2025-01-12T09:18:55.120147",
              "media": [],
              "parentPost": null
              }
          }
         ```

8. **Retrieve Comments/Replies**:
   - Endpoint: `GET /api/posts?post_id=1`
     - Response:
       ```json
            {
              "code": "SUC001",
              "message": "Request was successful",
              "data": {
                  "content": [
                      {
                          "id": 7,
                          "content": "Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum",
                          "author": {
                              "id": 2,
                              "email": "test-user@gmail.com",
                              "firstname": "John",
                              "lastname": "Doe"
                          },
                          "createdAt": "2025-01-12T09:18:55",
                          "media": [
                              {
                              "type": "image",
                              "url": "https://media.istockphoto.com/id/1458782106/photo/scenic-aerial-view-of-the-mountain-landscape-with-a-forest-and-the-crystal-blue-river-in.jpg?s=2048x2048&w=is&k=20&c=jbXMS_yFujUo29EIjPd8XBsEan-PAHUcPs0Zo1-HY_U="
                              }
                          ],
                          "parentPost": null
                      },
                      {
                          "id": 6,
                          "content": "Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum",
                          "author": {
                              "id": 2,
                              "email": "test-user@gmail.com",
                              "firstname": "John",
                              "lastname": "Doe"
                          },
                          "createdAt": "2025-01-12T09:18:30",
                          "media": [
                              {
                              "type": "image",
                              "url": "https://media.istockphoto.com/id/1458782106/photo/scenic-aerial-view-of-the-mountain-landscape-with-a-forest-and-the-crystal-blue-river-in.jpg?s=2048x2048&w=is&k=20&c=jbXMS_yFujUo29EIjPd8XBsEan-PAHUcPs0Zo1-HY_U="
                              }
                          ],
                          "parentPost": null
                      }
                  ],
                  "pageable": {
                      "pageNumber": 0,
                      "pageSize": 2,
                      "sort": {
                          "empty": false,
                          "sorted": true,
                          "unsorted": false
                      },
                      "offset": 0,
                      "paged": true,
                      "unpaged": false
                  },
                  "last": false,
                  "totalPages": 2,
                  "totalElements": 3,
                  "first": true,
                  "size": 2,
                  "number": 0,
                  "sort": {
                      "empty": false,
                      "sorted": true,
                      "unsorted": false
                  },
                  "numberOfElements": 2,
                  "empty": false
              }
            }
       ```

9. **Delete Post**:
    - Endpoint: `DELETE /api/post/{id}`
      - Response:
        ```json
           {
               "code": "SUC001",
               "message": "Request was successful",
               "data": null
           }
        ```
10. **Follow User**
    - Endpoint: `POST /api/follow`
      - Payload: // user id of the user to be followed 
        ```json
          {
              "following_id": 2 
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
11. **Unfollow User**
    - Endpoint: `DELETE /api/follow`
      - Payload: // user id of the user to be unfollowed
        ```json
          {
              "following_id": 2 
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
---

## API Endpoints

| Method | Endpoint                             | Description                                                                    |
|--------|--------------------------------------|--------------------------------------------------------------------------------|
| POST   | `/api/auth/create-account`           | Register a new user                                                            |
| POST   | `/api/auth/login`                    | Authenticate user                                                              |
| GET    | `/api/user`                          | Get Logged In user                                                             |
| POST   | `/api/post`                          | Create Post/Comment With Media                                                 |
| GET    | `/api/posts`                         | Retrieve a list of all posts                                                   | 
| GET    | `/api/post?page=0&size=15`           | Retrieve posts on page 0 with a maximum of 15 posts per page.                  | 
| GET    | `/api/post?page=0&size=5&author_id=2` | Retrieve posts authored by the user with ID = 2, on page 0, limited to 5 posts. | 
| GET    | `/api/post?page=0&size=5&post_id=1`  | Retrieve replies or comments for the post with ID = 1, on page 0, limited to 5. | 
| GET    | `/api/post/{id}`                     | Get Single Post with Media                                                     |
| DELETE | `/api/post/{id}`                     | Delete Single Post                                                             |
| GET    | `/api/post?&post_id=1`               | Retrieve comments for the post with ID = 1.                                    | 
| DELETE | `/api/post/{id}`                     | Delete Single Comment                                                          |
| POST   | `/api/follow/`                       | Follow a user                                                                  |
| DELETE | `/api/follow/`                       | Unfollow a user                                                                |

---

## **Postman Collection**

You can import the Postman collection to test the API endpoints directly.

1. **Download the Postman Collection**: [Folk Social API Postman Collection](https://documenter.getpostman.com/view/6055785/2sAYQWKtHh)
2. **Import into Postman**:
    - Open Postman.
    - Click on `Import` in the top-left corner.
    - Upload the collection file (JSON format).
3. **Start Testing**:
    - Configure the base URL for your API (e.g., `http://localhost:8080` or your deployed API URL).
    - Test the endpoints provided in the collection.

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


---

## Future Enhancements

- Add more user roles (e.g., admin, moderator).
- Implement advanced search and filtering for posts.
- Add file upload capabilities for images and videos.
- Enhance real-time capabilities with WebSocket integration.

