# MARLO - Backend Spring Boot Hexagonal + Clean Architecture

MARLO is a robust foundation for a modern, scalable, and portable backend, built with Spring Boot, Maven, hexagonal and clean architecture. It includes MySQL integration, unit testing, Swagger documentation, and is ready for local execution, Docker, and AWS Lambda deployment.

## Architecture

MARLO follows a Hexagonal (Ports & Adapters) and Clean Architecture approach:

- **Domain (domain/model):** Contains pure business models and logic, independent of frameworks or external technologies.
- **Application (application/service):** Holds business use cases and orchestrates domain logic.
- **Adapters (adapter/rest):** Entry and exit points, such as REST controllers, repositories, or external integrations.
- **Infrastructure (config, resources):** Technical details like database configuration, Swagger, and logging.

This separation ensures maintainability, testability, and flexibility, making it easy to evolve the system, swap technologies, and deploy in different environments (local, Docker, Lambda).

---

## Structure

- Spring Boot + Maven
- Hexagonal and clean architecture
- MySQL integration
- Example REST service
- Swagger configuration
- Ready for local, Docker, and Lambda

## Local Execution

1. Configure your MySQL database and update `application-local.properties` or use environment variables (`.env`).
2. Run:
   ```
   mvn spring-boot:run
   ```

## Docker

1. Build the image:
   ```
   docker build -t marlo-backend .
   ```
2. Run the container:
   ```
   docker run -p 8080:8080 --env-file .env marlo-backend
   ```

## AWS Lambda

- You can use AWS Serverless Java Container to package the app.
- See the official AWS documentation for instructions.

## Swagger

- Access the documentation at: `http://localhost:8080/swagger-ui.html` or `http://localhost:8080/swagger-ui/index.html`

---

---

**Best Practices:**

- Use environment variables for credentials and sensitive data (can be sourced from AWS Secrets Manager).
- Keep business logic in the domain and application layers, and technological details in the adapters.
- Ensure minimum test coverage (see JaCoCo configuration in the pom.xml).
- Document and version your API with Swagger/OpenAPI.
- For Lambda, package using AWS Serverless Java Container.

## Logging

MARLO uses Spring Boot's built-in logging (SLF4J with Logback). You can log messages in your code using:

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

private static final Logger logger = LoggerFactory.getLogger(YourClass.class);

logger.info("This is an info log message");
logger.error("This is an error log message");
```

You can configure log levels and output in `src/main/resources/application.yml` or by providing environment variables.
