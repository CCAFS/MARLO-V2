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

### Requirements
- Java 17 (JDK 17)
- Maven 3.6+
- MySQL 8.0+ with `aiccradb` database

### Quick Start

1. **Use the automated script:**
   ```bash
   ./run-marlo.sh
   ```

2. **Test the application:**
   ```bash
   ./test-marlo.sh
   ```

### Manual Execution

1. Configure Java 17:
   ```bash
   export JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home"
   export PATH="$JAVA_HOME/bin:$PATH"
   ```

2. Compile and run:
   ```bash
   mvn compile
   mvn spring-boot:run -Dspring-boot.run.profiles=local
   ```

### Important URLs
- **API Base:** http://localhost:8080/api/innovations
- **Swagger UI:** http://localhost:8080/swagger-ui/index.html
- **OpenAPI Docs:** http://localhost:8080/v3/api-docs

### Main Endpoints
- `GET /api/innovations` - All active innovations
- `GET /api/innovations/{id}` - Specific innovation with actors
- `GET /api/innovations/phase/{phaseId}/with-actors` - Innovations with actors by phase
- `GET /api/innovations/search?projectId=X&active=true` - Search by project

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

## AWS Lambda (Imagen de contenedor)

1. Construye el artefacto: `mvn clean package` (genera `target/marlo-0.0.1-SNAPSHOT.jar`).
2. Crea la imagen para Lambda: `docker build -f Dockerfile.lambda -t marlo-lambda .`.
3. (Opcional) Prueba la imagen local con el Runtime Interface Emulator de AWS: `docker run -p 9000:8080 marlo-lambda` y realiza una invocaci�n HTTP a `http://localhost:9000/2015-03-31/functions/function/invocations` con el payload de API Gateway.
4. Publica la imagen en ECR y �sala en tu funci�n Lambda.

La aplicaci�n sigue ejecut�ndose de forma tradicional con `mvn spring-boot:run` o `java -jar target/marlo-0.0.1-SNAPSHOT.jar`.
## AWS Lambda (Imagen de contenedor)

1. Construye el artefacto: mvn clean package (genera 	arget/marlo-0.0.1-SNAPSHOT.jar).
2. Crea la imagen para Lambda: docker build -f Dockerfile.lambda -t marlo-lambda ..
3. (Opcional) Prueba la imagen local con el Runtime Interface Emulator de AWS: docker run -p 9000:8080 marlo-lambda y realiza una invocaci�n HTTP a http://localhost:9000/2015-03-31/functions/function/invocations con el payload de API Gateway.
4. Publica la imagen en ECR y �sala en tu funci�n Lambda.

La aplicaci�n sigue ejecut�ndose de forma tradicional con mvn spring-boot:run o java -jar target/marlo-0.0.1-SNAPSHOT.jar.

## Configuraci�n de Swagger detr�s de API Gateway

- La aplicaci�n espera el prefijo del stage (/dev, /prod, etc.) en la variable de entorno BASE_PATH. Localmente puedes omitirla (queda vac�a).
- En Lambda: BASE_PATH=/dev (o el stage correspondiente) junto con el resto de variables que utilices (SPRING_PROFILES_ACTIVE, MYSQL_URL, etc.).
- El swagger queda accesible en ${BASE_PATH}/swagger-ui/index.html, y los docs en ${BASE_PATH}/v3/api-docs.
- Cualquier despliegue nuevo: reconstruye el JAR, crea la imagen, s�bela a ECR, actualiza la funci�n y despliega el API Gateway.
## Configuraci�n de Swagger detr�s de API Gateway

- Define la variable de entorno BASE_PATH con el prefijo de tu stage (/dev, /prod, etc.). En local puedes dejarla vac�a.
- Mant�n el resto de variables (por ejemplo SPRING_PROFILES_ACTIVE, MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD).
- Swagger queda disponible en ${BASE_PATH}/swagger-ui/index.html y los docs en ${BASE_PATH}/v3/api-docs.
- Para despliegues: mvn clean package, docker build -f Dockerfile.lambda ..., push a ECR, ws lambda update-function-code ... con la nueva imagen y ws lambda update-function-configuration ... --environment "Variables={BASE_PATH=/dev,...}. Luego Deploy API en API Gateway.

Localmente, sin BASE_PATH, la UI sigue en http://localhost:8080/swagger-ui/index.html.
