# Extrabite Backend

A Spring Boot-based backend application for the ExtraBite platform, providing a comprehensive API for food donation and community sharing services.

## 🍽️ What is Extrabite?

Extrabite is a platform that connects food donors with recipients, facilitating food sharing and reducing waste in communities. The backend provides RESTful APIs for user management, donation handling, request processing, ratings, and directory services.

## 🏗️ Architecture & Technology Stack

- **Framework**: Spring Boot 3.5.0
- **Language**: Java 17
- **Database**: PostgreSQL with JPA/Hibernate
- **Security**: Spring Security with JWT authentication
- **API Documentation**: OpenAPI 3 with Swagger UI
- **Build Tool**: Maven
- **Containerization**: Docker

## 🚀 Key Features

- **User Management**: Registration, authentication, and profile management
- **Donation System**: Create, manage, and track food donations
- **Request Processing**: Handle donation requests and confirmations
- **Rating System**: User and donation ratings
- **Directory Services**: Find and browse available donations
- **Admin Panel**: Administrative functions and user management
- **API Key Authentication**: Secure API access with custom API keys
- **Framework**: Spring Boot 3.5.0  
- **Language**: Java 17  
- **Database**: PostgreSQL with JPA/Hibernate  
- **Security**: Spring Security with JWT authentication  
- **API Documentation**: OpenAPI 3 with Swagger UI  
- **Build Tool**: Maven  
- **Containerization**: Docker  

## 🚀 Key Features

- **User Management**: Registration, authentication, and profile management  
- **Donation System**: Create, manage, and track food donations  
- **Request Processing**: Handle donation requests and confirmations  
- **Rating System**: User and donation ratings  
- **Directory Services**: Find and browse available donations  
- **Admin Panel**: Administrative functions and user management  
- **API Key Authentication**: Secure API access with custom API keys  

## 📁 Project Structure

```
src/main/java/com/extrabite/
├── config/          # Security and configuration classes
├── controller/      # REST API controllers
├── dto/            # Data Transfer Objects
├── entity/         # JPA entities
├── repository/     # Data access layer
├── service/        # Business logic layer
│   ├── impl/       # Service implementations
│   └── spec/       # JPA specifications
├── util/           # Utility classes
└── scheduler/      # Scheduled tasks
├── dto/             # Data Transfer Objects
├── entity/          # JPA entities
├── repository/      # Data access layer
├── service/         # Business logic layer
│   ├── impl/        # Service implementations
│   └── spec/        # JPA specifications
├── util/            # Utility classes
└── scheduler/       # Scheduled tasks
```

## 🛠️ Development Setup

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL database
- Docker (optional)

### Environment Configuration

1. Copy `env.example` to `.env` in the root directory
2. Configure the required environment variables as described in [ENV_SETUP.md](ENV_SETUP.md)
- Java 17 or higher  
- Maven 3.6+  
- PostgreSQL database  
- Docker (optional)  

### Environment Configuration

1. Copy `env.example` to `.env` in the root directory.  
2. Configure the required environment variables as described in [ENV_SETUP.md](ENV_SETUP.md).  

### Running the Application

#### Using Maven
```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

#### Using Docker
```bash
# Build and run with Docker
docker build -t extrabite-backend .
docker run -p 8080:8080 extrabite-backend
```

### Development Tools
- **Swagger UI**: Available at `https://extrabite-backend-2.onrender.com/swagger-ui/index.html#/`
- **Actuator Endpoints**: Health checks and metrics at `/actuator`
- **DevTools**: Hot reload enabled for development

## 🔐 Authentication & Security

The application uses multiple authentication mechanisms:

- **API Key Authentication**: Required for all API endpoints
- **JWT Authentication**: For user sessions and protected operations
- **Role-based Access Control**: Different permissions for users and admins

For detailed authentication setup, see [API Key Usage Documentation](API_USE_DOCS/1_API_KEY_USAGE.md).
- **API Key Authentication**: Required for all API endpoints  
- **JWT Authentication**: For user sessions and protected operations  
- **Role-based Access Control**: Different permissions for users and admins  

For detailed authentication setup, see [API Key Usage Documentation](API_USE_DOCS/1_API_KEY_USAGE.md).  

## 📚 API Documentation

The Extrabite Backend provides comprehensive REST APIs organized into the following modules:

### Core Modules
- **[Authentication API](API_USE_DOCS/2_AUTH_API_DOCS.md)** - User registration, login, and session management
- **[User Management API](API_USE_DOCS/3_USER_MODULE_API_DOCS.md)** - User profiles, updates, and account management
- **[Directory API](API_USE_DOCS/4_FIND_DIRECTORY_API_DOCS.md)** - Search and browse functionality
- **[Donation API](API_USE_DOCS/5_DONATION_API_DOCS.md)** - Create, manage, and track donations
- **[Browse API](API_USE_DOCS/6_BROWSE_API_DOCS.md)** - Advanced search and filtering
- **[Requests API](API_USE_DOCS/7_REQUESTS_API_DOCS.md)** - Handle donation requests and confirmations
- **[Rating API](API_USE_DOCS/8_RATING_API_DOCS.md)** - User and donation rating system

### Interactive API Documentation
- **Swagger UI**: Visit `https://extrabite-backend-2.onrender.com/swagger-ui/index.html#/` for interactive API documentation
- **OpenAPI Specification**: Available at `https://extrabite-backend-2.onrender.com/v3/api-docs`
- **[Authentication API](API_USE_DOCS/2_AUTH_API_DOCS.md)** – User registration, login, and session management  
- **[User Management API](API_USE_DOCS/3_USER_MODULE_API_DOCS.md)** – User profiles, updates, and account management  
- **[Directory API](API_USE_DOCS/4_FIND_DIRECTORY_API_DOCS.md)** – Search and browse functionality  
- **[Donation API](API_USE_DOCS/5_DONATION_API_DOCS.md)** – Create, manage, and track donations  
- **[Browse API](API_USE_DOCS/6_BROWSE_API_DOCS.md)** – Advanced search and filtering  
- **[Requests API](API_USE_DOCS/7_REQUESTS_API_DOCS.md)** – Handle donation requests and confirmations  
- **[Rating API](API_USE_DOCS/8_RATING_API_DOCS.md)** – User and donation rating system  

## 🧪 Testing

```bash
# Run all tests
mvn test

# Run tests with coverage
mvn test jacoco:report
```

## 📦 Deployment

### Docker Deployment
```bash
# Build Docker image
docker build -t extrabite-backend .

# Run container
docker run -d -p 8080:8080 --env-file .env extrabite-backend
```

### Production Considerations
- Configure production database
- Set up proper SSL/TLS certificates
- Configure logging and monitoring
- Set up backup strategies
- Use environment-specific configurations

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass
6. Submit a pull request

## 📄 License

This project is licensed under the terms specified in the project's license file.

- Configure production database  
- Set up proper SSL/TLS certificates  
- Configure logging and monitoring  
- Set up backup strategies  
- Use environment-specific configurations  

## 🤝 Contributing

1. Fork the repository  
2. Create a feature branch  
3. Make your changes  
4. Add tests for new functionality  
5. Ensure all tests pass  
6. Submit a pull request  

## 📄 License

This project is licensed under the terms specified in the project’s license file.

## 🆘 Support

For technical support or questions about the API:
- Check the [API Documentation](API_USE_DOCS/) for detailed endpoint information
- Review the [Environment Setup Guide](ENV_SETUP.md) for configuration help
- Open an issue in the project repository

---

**Note**: This README provides an overview of the Extrabite Backend project. For detailed API usage, authentication, and endpoint documentation, please refer to the comprehensive documentation in the [API_USE_DOCS/](API_USE_DOCS/) directory. 
=======

- Check the [API Documentation](API_USE_DOCS/) for detailed endpoint information  
- Review the [Environment Setup Guide](ENV_SETUP.md) for configuration help  
- Open an issue in the project repository  

---

**Note**: This README provides an overview of the Extrabite Backend project. For detailed API usage, authentication, and endpoint documentation, please refer to the comprehensive documentation in the [API_USE_DOCS/](API_USE_DOCS/) directory.

## 💬 Suggestion and Query

For any suggestion and query, please contact the developer at **er.alokmaurya22@gmail.com**.

---

Let me know if you'd like to convert this into a styled GitHub README.md file or want a version with badges and indicators for build/test status, license, etc. 
