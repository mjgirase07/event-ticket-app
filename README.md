# event-ticket-app

<!-- Build status: replace with CI badge -->

![Java Version](https://img.shields.io/badge/java-21-blue)

## Short Description

event-ticket-app is a Java-based backend application that provides a secure, production-ready REST API for managing events and ticketing workflows. It is intended for developers and teams building ticketing platforms, event management tools, or integrations that require robust authentication, concurrent-safe ticket sales, and QR-code based validation.

## Features

- Secure OAuth2/JWT authentication with Keycloak integration and role-based access control (RBAC)
- Event creation, update, publishing and listing (published/unpublished views)
- Ticket purchasing with transactional inventory checks and pessimistic locking to prevent oversell
- Ticket type management (pricing, capacity, constraints)
- QR code generation at purchase and one-time-use QR validation pipeline
- Full-text search and paginated listings using PostgreSQL native text search
- Centralized error handling and DTO validation via Jakarta Bean Validation
- MapStruct DTO mapping and layered service/repository architecture
- Roles based acess wher roles are staff, organizer, attendee.`

## Tech Stack

- Language: Java 21
- Framework: Spring Boot (see [backend/tickets/pom.xml](backend/tickets/pom.xml))
- Build: Maven
- Database: PostgreSQL (H2 available for local/dev quickstarts)
- Mapping: MapStruct
- Security: Spring Security + OAuth2 Resource Server (Keycloak integration expected)
- QR Code: ZXing
- Testing: Spring Boot test slices

## Architecture Overview

The backend follows a layered, monolithic architecture exposing a versioned REST API at `http://localhost:8080/api/v1`. Key layers include:

- Controllers: REST API entrypoints (request validation, mapping to DTOs)
- Services: Business logic and transactional boundaries
- Repositories: Spring Data JPA repositories against PostgreSQL
- Mappers: MapStruct mappers for DTO ↔ entity conversions
- Security layer: OAuth2/JWT resource-server configuration and a just-in-time provisioning filter

This architecture emphasizes clear separation of concerns, transactional safety for purchase flows, and small, testable service units.

## Getting Started

### Prerequisites

- Java 21 installed and `JAVA_HOME` configured
- Maven 3.8+ installed
- PostgreSQL 12+ (or compatible) for production-like runs
- Optional: Keycloak (or another OAuth2 provider) for JWT authentication

### Clone the repository

```bash
git clone https://github.com/mjgirase07/event-ticket-app
cd event-ticket-app
```

### Build and run locally

From the project root run the module build and start Spring Boot (module is `backend/tickets`):

```bash
# build
mvn -f backend/tickets clean package -DskipTests

# run (executable jar)
java -jar "backend/tickets/target/tickets-0.0.1-SNAPSHOT.jar"

# or run via maven (dev mode)
mvn -f backend/tickets spring-boot:run
```

By default the API base URL is: `http://localhost:8080/api/v1`

### Environment variables / Configuration

Configuration is driven by Spring Boot conventions and `application.properties`  in [backend/tickets/src/main/resources/application.properties](backend/tickets/src/main/resources/application.properties). Common variables to set (or place into `application.properties` :

- `spring.datasource.url` — JDBC URL for PostgreSQL (e.g. `jdbc:postgresql://localhost:5432/ticketsdb`)
- `spring.datasource.username` — DB username
- `spring.datasource.password` — DB password
- `spring.datasource.driver-class-name` — org.postgresql.Driver

- `spring.security.oauth2.resourceserver.jwt.issuer-uri` — Keycloak / IdP configuration


 ``.

## API Endpoints

Base path: `http://localhost:8080/api/v1`

| Method | Path | Description |
|--------|------|-------------|
| GET | /events | List all events (supports pagination & search) |
| POST | /events | Create a new event (requires auth, role: organizer) |
| GET | /events/{id} | Get event details by id |
| PUT | /events/{id} | Update event (requires auth, organizer) |
| DELETE | /events/{id} | Delete event (requires auth, organizer) |
| GET | /published-events | List published events (public) |
| GET | /ticket-types | List ticket types for an event (filter by eventId) |
| POST | /ticket-types | Create ticket type (requires auth, organizer) |
| PUT | /ticket-types/{id} | Update ticket type (requires auth, organizer) |
| POST | /tickets/purchase | Purchase ticket(s) — transactional, prevents oversell |
| GET | /tickets/{id} | Get ticket details (owner or admin) |
| POST | /tickets/{id}/qrcode | Generate or retrieve QR code for ticket |
| POST | /tickets/validate | Validate a QR code (one-time use) |


- Authentication: most write operations require JWT with roles; public read endpoints exist for published content.

## Project Structure

Top-level module: `backend/tickets`

```
backend/tickets
├─ mvnw, mvnw.cmd, pom.xml                      # Maven wrapper and module pom
├─ src/main/java/com/mahendra/tickets
│  ├─ TicketsApplication.java                  # Spring Boot application entrypoint
│  ├─ config/                                  # Security, JPA, QR config classes
│  ├─ controllers/                              # REST controllers (Events, Tickets, Validation)
│  ├─ domain/                                   # DTOs, request/response models, entities
│  ├─ exceptions/                               # Custom exception types and handlers
│  ├─ filters/                                  # JWT provisioning filter and other servlet filters
│  ├─ mappers/                                  # MapStruct mappers (DTO <-> Entity)
│  ├─ repositories/                              # Spring Data JPA repositories
│  └─ services/                                 # Business logic services and impls
├─ src/main/resources/application.properties    # App configuration (datasource, security)
```

See the generated sources for MapStruct implementations under `target/generated-sources/annotations` when you build the project.

## Contributing

Contributions are welcome. Suggested workflow:

1. Fork the repository
2. Create a feature branch: `git checkout -b feat/your-feature`
3. Implement your changes, add tests where appropriate
4. Run tests and linters locally
5. Open a Pull Request describing the change and linking any relevant issues

Please follow the existing code style and add unit/integration tests for non-trivial changes.

