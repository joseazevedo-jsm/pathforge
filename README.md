# PathForge

## About

PathForge is a REST API for tracking learning skills, estimated study time, and completion progress. It is a portfolio project for practicing modern Java, Spring Boot, database persistence, validation, migrations, and automated testing.

## Features

- Create, list, retrieve, complete, and delete skills
- Validate incoming requests and return field-level errors
- Persist data in PostgreSQL through Spring Data JPA
- Version the database schema with Flyway
- Run PostgreSQL locally with Docker Compose
- Test domain logic, services, dependency injection, and HTTP endpoints

## Tech Stack

- Java 21
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA and Hibernate
- PostgreSQL 17
- Flyway
- Docker Compose
- JUnit 5, Mockito, MockMvc, and H2
- Maven Wrapper

## Architecture

```text
HTTP request
    -> SkillController
    -> SkillService
    -> SkillRepository
    -> PostgreSQL
```

Controllers handle HTTP, the service contains application behavior, and the repository manages persistence. Production uses PostgreSQL, while automated tests use an isolated H2 database.

## Running Locally

Requirements:

- Java 21
- Docker with Docker Compose

Create the local environment file and choose development database credentials:

```bash
cp .env.example .env
```

Start PostgreSQL and wait until it is healthy:

```bash
docker compose up -d
docker compose ps
```

Start the API:

```bash
./mvnw spring-boot:run
```

The API is available at `http://localhost:8080`. Flyway applies pending migrations when the application starts, and Hibernate validates the resulting schema.

## API Endpoints

| Method | Endpoint | Description | Success status |
|---|---|---|---|
| `GET` | `/api/skills` | List all skills | `200 OK` |
| `GET` | `/api/skills/{id}` | Retrieve a skill by ID | `200 OK` |
| `POST` | `/api/skills` | Create a skill | `201 Created` |
| `PATCH` | `/api/skills/{id}/complete` | Mark a skill completed | `200 OK` |
| `DELETE` | `/api/skills/{id}` | Delete a skill | `204 No Content` |

Example request:

```bash
curl -X POST http://localhost:8080/api/skills \
  -H "Content-Type: application/json" \
  -d '{"name":"Spring Boot","estimatedHours":12}'
```

Invalid requests return `400 Bad Request` with field-level validation messages. Missing IDs return `404 Not Found`.

## Running Tests

```bash
./mvnw test
```

The test configuration uses H2 in PostgreSQL compatibility mode, so running the test suite does not modify the local PostgreSQL database.

## Stopping the Environment

Stop the containers while preserving database data:

```bash
docker compose down
```

Adding `-v` also deletes the local PostgreSQL volume and all stored PathForge data.
