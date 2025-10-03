# Configuration Environments - MARLO Project

## 📋 Available Configuration Files

### 🏠 **Local Development**: `application-local.properties`
- **Database**: MySQL local (localhost:3306)
- **Credentials**: root/00000000
- **PDF Service**: http://localhost:3001/api/pdf/innovation-report
- **Usage**: `--spring.profiles.active=local`

### 🔧 **Development**: `application-dev.properties`
- **Database**: Development MySQL server
- **Credentials**: aiccra_dev/dev_password_2024
- **PDF Service**: http://dev-pdf-service:3001/api/pdf/innovation-report
- **Usage**: `--spring.profiles.active=dev`

### 🚀 **Production**: `application-prod.properties`
- **Database**: Production MySQL cluster
- **Credentials**: Environment variables (DB_USERNAME, DB_PASSWORD)
- **PDF Service**: https://pdf-service.aiccra.org/api/pdf/innovation-report
- **Usage**: `--spring.profiles.active=prod`

### 🧪 **Testing**: `application-test.properties`
- **Database**: H2 in-memory database
- **Credentials**: sa (no password)
- **PDF Service**: Mock service for testing
- **Usage**: Automatically used during tests

## 🎯 How to Use

### Command Line
```bash
# Local environment
java -jar marlo.jar --spring.profiles.active=local

# Development environment
java -jar marlo.jar --spring.profiles.active=dev

# Production environment
java -jar marlo.jar --spring.profiles.active=prod
```

### Maven
```bash
# Local environment
mvn spring-boot:run -Dspring-boot.run.profiles=local

# Development environment
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Production environment
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

### Environment Variables (Production)
```bash
export DB_USERNAME=prod_user
export DB_PASSWORD=secure_password
export PDF_GENERATOR_URL=https://pdf-service.aiccra.org/api/pdf/innovation-report
```

## 🔧 Configuration Properties

### PDF Report Generator
```properties
# The URL of the PDF generation service
marlo.innovation.pdf-generator.url=http://your-pdf-service/api/pdf/innovation-report
```

### Database Configuration
```properties
# MySQL connection settings
spring.datasource.url=jdbc:mysql://host:port/database
spring.datasource.username=username
spring.datasource.password=password
```

## 🛡️ Security Notes

- **Local/Dev**: Use fixed credentials for development convenience
- **Production**: Always use environment variables for sensitive data
- **Testing**: Use in-memory database to avoid external dependencies

## 📝 Adding New Environments

1. Create new file: `application-{env}.properties`
2. Copy base configuration from existing file
3. Modify environment-specific values
4. Use with: `--spring.profiles.active={env}`