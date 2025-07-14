# Dev Commute

A monolithic Spring Boot ride-sharing application for IT professionals, featuring Apache Kafka event-driven flows and PostgreSQL persistence.

## Features
- Publish and browse rides
- Join requests and owner acceptance
- User profiles
- Kafka integration with multiple consumer groups, dead letter queue, and Kafka Streams
- PostgreSQL persistence (via Docker)

---

## Prerequisites
- Java 21 (Eclipse Temurin recommended)
- Maven
- Docker & Docker Compose

---

## Quick Start

### 1. Start Kafka and PostgreSQL (Docker Compose)
Navigate to the `kafka-setup` directory and start the stack:

```sh
cd ../kafka-setup
# Start Kafka cluster, Kafka UI, and PostgreSQL
# (Adjust path if running from project root)
docker compose up -d
```

### 2. Build the Spring Boot App (Maven)

```sh
cd ../dev-commute
mvn clean package -DskipTests
```

### 3. Build Docker Image

```sh
docker build -t dev-commute:latest .
```

### 4. Run the Application Container

Replace `<network-name>` with the actual Docker network name (e.g., `kafka-setup_kafka-net`):

```sh
docker run --rm -p 8081:8081 \
  --name dev-commute-app \
  --network <network-name> \
  -e KAFKA_BOOTSTRAP_SERVERS="broker-1:9092,broker-2:9093,broker-3:9094" \
  dev-commute:latest
```

- The app will be available at http://localhost:8081
- Default REST endpoints: `/users`, `/rides`, `/joinRequests`, etc.

### 5. Example: Create a User

```sh
curl -X POST http://localhost:8081/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "alice",
    "name": "Alice Example",
    "email": "alice@example.com"
  }'
```

---

## Notes
- Hibernate auto-DDL is enabled: tables are created/updated automatically from entities.
- Update the Docker network name as per your Compose project (see `docker network ls`).
- For production, consider using Flyway/Liquibase for migrations and disabling auto-DDL.

---

## License
MIT
