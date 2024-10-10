# StudyCardsAPI - PLACEHOLDER README

StudyCardsAPI is a Spring Boot application designed to manage study cards. This project includes a RESTful API with endpoints for user management, authentication, and card conversion. The project is containerized using Docker and can be orchestrated using Docker Compose.

## Table of Contents

-   Prerequisites
-   Installation
-   Configuration
-   [Building and Running](#building-and-running)
-   Testing
-   [API Documentation](#api-documentation)
-   Contributing
-   License

## Prerequisites

-   Java 21
-   Maven 3.9.9
-   Docker
-   Docker Compose

## Installation

1. **Clone the repository:**

    ```sh
    git clone https://github.com/yourusername/StudyCardsAPI.git
    cd StudyCardsAPI
    ```

2. **Set up environment variables:**

    Copy the

.env-example

file to

.env

and update the values as needed.

    ```sh
    cp .env-example .env
    ```

## Configuration

### Application Properties

The application configuration is managed through the

application.properties

file. Key properties include:

-   [`spring.datasource.url`](command:_github.copilot.openSymbolFromReferences?%5B%22%22%2C%5B%7B%22uri%22%3A%7B%22scheme%22%3A%22file%22%2C%22authority%22%3A%22%22%2C%22path%22%3A%22%2Fhome%2Fgustavo%2FDevelopment%2FStudyCardsAPI%2Fsrc%2Fmain%2Fresources%2Fapplication.properties%22%2C%22query%22%3A%22%22%2C%22fragment%22%3A%22%22%7D%2C%22pos%22%3A%7B%22line%22%3A9%2C%22character%22%3A0%7D%7D%5D%2C%22cced0671-ddda-4365-b05a-37027f81521f%22%5D "Go to definition")
-   [`spring.datasource.username`](command:_github.copilot.openSymbolFromReferences?%5B%22%22%2C%5B%7B%22uri%22%3A%7B%22scheme%22%3A%22file%22%2C%22authority%22%3A%22%22%2C%22path%22%3A%22%2Fhome%2Fgustavo%2FDevelopment%2FStudyCardsAPI%2Fsrc%2Fmain%2Fresources%2Fapplication.properties%22%2C%22query%22%3A%22%22%2C%22fragment%22%3A%22%22%7D%2C%22pos%22%3A%7B%22line%22%3A10%2C%22character%22%3A0%7D%7D%5D%2C%22cced0671-ddda-4365-b05a-37027f81521f%22%5D "Go to definition")
-   [`spring.datasource.password`](command:_github.copilot.openSymbolFromReferences?%5B%22%22%2C%5B%7B%22uri%22%3A%7B%22scheme%22%3A%22file%22%2C%22authority%22%3A%22%22%2C%22path%22%3A%22%2Fhome%2Fgustavo%2FDevelopment%2FStudyCardsAPI%2Fsrc%2Fmain%2Fresources%2Fapplication.properties%22%2C%22query%22%3A%22%22%2C%22fragment%22%3A%22%22%7D%2C%22pos%22%3A%7B%22line%22%3A11%2C%22character%22%3A0%7D%7D%5D%2C%22cced0671-ddda-4365-b05a-37027f81521f%22%5D "Go to definition")
-   [`spring.mail.host`](command:_github.copilot.openSymbolFromReferences?%5B%22%22%2C%5B%7B%22uri%22%3A%7B%22scheme%22%3A%22file%22%2C%22authority%22%3A%22%22%2C%22path%22%3A%22%2Fhome%2Fgustavo%2FDevelopment%2FStudyCardsAPI%2Fsrc%2Fmain%2Fresources%2Fapplication.properties%22%2C%22query%22%3A%22%22%2C%22fragment%22%3A%22%22%7D%2C%22pos%22%3A%7B%22line%22%3A34%2C%22character%22%3A0%7D%7D%5D%2C%22cced0671-ddda-4365-b05a-37027f81521f%22%5D "Go to definition")
-   [`spring.mail.port`](command:_github.copilot.openSymbolFromReferences?%5B%22%22%2C%5B%7B%22uri%22%3A%7B%22scheme%22%3A%22file%22%2C%22authority%22%3A%22%22%2C%22path%22%3A%22%2Fhome%2Fgustavo%2FDevelopment%2FStudyCardsAPI%2Fsrc%2Fmain%2Fresources%2Fapplication.properties%22%2C%22query%22%3A%22%22%2C%22fragment%22%3A%22%22%7D%2C%22pos%22%3A%7B%22line%22%3A35%2C%22character%22%3A0%7D%7D%5D%2C%22cced0671-ddda-4365-b05a-37027f81521f%22%5D "Go to definition")
-   [`spring.mail.username`](command:_github.copilot.openSymbolFromReferences?%5B%22%22%2C%5B%7B%22uri%22%3A%7B%22scheme%22%3A%22file%22%2C%22authority%22%3A%22%22%2C%22path%22%3A%22%2Fhome%2Fgustavo%2FDevelopment%2FStudyCardsAPI%2Fsrc%2Fmain%2Fresources%2Fapplication.properties%22%2C%22query%22%3A%22%22%2C%22fragment%22%3A%22%22%7D%2C%22pos%22%3A%7B%22line%22%3A36%2C%22character%22%3A0%7D%7D%5D%2C%22cced0671-ddda-4365-b05a-37027f81521f%22%5D "Go to definition")
-   [`spring.mail.password`](command:_github.copilot.openSymbolFromReferences?%5B%22%22%2C%5B%7B%22uri%22%3A%7B%22scheme%22%3A%22file%22%2C%22authority%22%3A%22%22%2C%22path%22%3A%22%2Fhome%2Fgustavo%2FDevelopment%2FStudyCardsAPI%2Fsrc%2Fmain%2Fresources%2Fapplication.properties%22%2C%22query%22%3A%22%22%2C%22fragment%22%3A%22%22%7D%2C%22pos%22%3A%7B%22line%22%3A37%2C%22character%22%3A0%7D%7D%5D%2C%22cced0671-ddda-4365-b05a-37027f81521f%22%5D "Go to definition")

### Docker Compose

The

compose.yaml

file defines the services for the application and the database. Key configurations include:

-   [`DATABASE_URL`](command:_github.copilot.openSymbolFromReferences?%5B%22%22%2C%5B%7B%22uri%22%3A%7B%22scheme%22%3A%22file%22%2C%22authority%22%3A%22%22%2C%22path%22%3A%22%2Fhome%2Fgustavo%2FDevelopment%2FStudyCardsAPI%2Fcompose.yaml%22%2C%22query%22%3A%22%22%2C%22fragment%22%3A%22%22%7D%2C%22pos%22%3A%7B%22line%22%3A9%2C%22character%22%3A6%7D%7D%2C%7B%22uri%22%3A%7B%22scheme%22%3A%22file%22%2C%22authority%22%3A%22%22%2C%22path%22%3A%22%2Fhome%2Fgustavo%2FDevelopment%2FStudyCardsAPI%2Fsrc%2Fmain%2Fresources%2Fapplication.properties%22%2C%22query%22%3A%22%22%2C%22fragment%22%3A%22%22%7D%2C%22pos%22%3A%7B%22line%22%3A9%2C%22character%22%3A24%7D%7D%5D%2C%22cced0671-ddda-4365-b05a-37027f81521f%22%5D "Go to definition")
-   [`DATABASE_USERNAME`](command:_github.copilot.openSymbolFromReferences?%5B%22%22%2C%5B%7B%22uri%22%3A%7B%22scheme%22%3A%22file%22%2C%22authority%22%3A%22%22%2C%22path%22%3A%22%2Fhome%2Fgustavo%2FDevelopment%2FStudyCardsAPI%2Fcompose.yaml%22%2C%22query%22%3A%22%22%2C%22fragment%22%3A%22%22%7D%2C%22pos%22%3A%7B%22line%22%3A10%2C%22character%22%3A6%7D%7D%2C%7B%22uri%22%3A%7B%22scheme%22%3A%22file%22%2C%22authority%22%3A%22%22%2C%22path%22%3A%22%2Fhome%2Fgustavo%2FDevelopment%2FStudyCardsAPI%2Fsrc%2Fmain%2Fresources%2Fapplication.properties%22%2C%22query%22%3A%22%22%2C%22fragment%22%3A%22%22%7D%2C%22pos%22%3A%7B%22line%22%3A10%2C%22character%22%3A29%7D%7D%5D%2C%22cced0671-ddda-4365-b05a-37027f81521f%22%5D "Go to definition")
-   [`DATABASE_PASSWORD`](command:_github.copilot.openSymbolFromReferences?%5B%22%22%2C%5B%7B%22uri%22%3A%7B%22scheme%22%3A%22file%22%2C%22authority%22%3A%22%22%2C%22path%22%3A%22%2Fhome%2Fgustavo%2FDevelopment%2FStudyCardsAPI%2Fcompose.yaml%22%2C%22query%22%3A%22%22%2C%22fragment%22%3A%22%22%7D%2C%22pos%22%3A%7B%22line%22%3A11%2C%22character%22%3A6%7D%7D%2C%7B%22uri%22%3A%7B%22scheme%22%3A%22file%22%2C%22authority%22%3A%22%22%2C%22path%22%3A%22%2Fhome%2Fgustavo%2FDevelopment%2FStudyCardsAPI%2Fsrc%2Fmain%2Fresources%2Fapplication.properties%22%2C%22query%22%3A%22%22%2C%22fragment%22%3A%22%22%7D%2C%22pos%22%3A%7B%22line%22%3A11%2C%22character%22%3A29%7D%7D%5D%2C%22cced0671-ddda-4365-b05a-37027f81521f%22%5D "Go to definition")

## Building and Running

### Using Maven

1. **Build the project:**

    ```sh
    ./mvnw clean package -DskipTests
    ```

2. **Run the application:**

    ```sh
    java -jar target/StudyCardsAPI-0.0.1-SNAPSHOT.jar
    ```

### Using Docker

1. **Build and run the containers:**

    ```sh
    docker-compose up --build
    ```

2. **Access the application:**

    The application will be available at `http://localhost:8080`.

## Testing

### Running Unit Tests

To run the unit tests, use the following command:

```sh
./mvnw test
```

### Test Reports

Test reports are generated in the

surefire-reports

directory.

## API Documentation

The API documentation is generated using Swagger and is available at `http://localhost:8080/swagger-ui.html`.

### Swagger Configuration

The Swagger configuration is defined in the [`SwaggerConfig`](command:_github.copilot.openSymbolInFile?%5B%7B%22scheme%22%3A%22file%22%2C%22authority%22%3A%22%22%2C%22path%22%3A%22%2Fhome%2Fgustavo%2FDevelopment%2FStudyCardsAPI%2Fsrc%2Fmain%2Fjava%2Fcom%2Fgorges%2Fstudycardsapi%2Fconfig%2FSwaggerConfig.java%22%2C%22query%22%3A%22%22%2C%22fragment%22%3A%22%22%7D%2C%22SwaggerConfig%22%2C%22cced0671-ddda-4365-b05a-37027f81521f%22%5D "/home/gustavo/Development/StudyCardsAPI/src/main/java/com/gorges/studycardsapi/config/SwaggerConfig.java") class.

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -am 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Create a new Pull Request.

## License

This project is licensed under the Apache License 2.0. See the LICENSE file for details.

---

For more information, please refer to the [Spring Boot Documentation](https://spring.io/projects/spring-boot) and the [Docker Documentation](https://docs.docker.com/).
