# Next Step Users Service

## Overview
User management and authentication service for the Next Step platform. Handles user registration, authentication, and profile management.

## Features
- User registration and authentication
- Firebase integration
- Role-based access control
- Profile management

## Tech Stack
- Spring Boot (latest-stable version)
- PostgreSQL
- Spring Security
- Firebase Authentication

## Setup
1. Configure environment variables:
   ```env
   SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/users_db
   SPRING_DATASOURCE_USERNAME=myuser
   SPRING_DATASOURCE_PASSWORD=secret
   FIREBASE_CONFIG_PATH=/path/to/firebase-config.json
   GATEWAY_URL=http://localhost:8080
   EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://localhost:8761/eureka/
   ```

2. Run PostgreSQL:
   ```bash
   docker-compose up -d
   ```

3. Build and run:
   ```bash
   ./mvnw spring-boot:run
   ```

## API Documentation
See [docs/api.md](docs/api.md) for detailed API documentation.

## Database Schema
See [docs/database.md](docs/database.md) for database schema details.

## Development Guide
See [docs/development.md](docs/development.md) for development guidelines.
