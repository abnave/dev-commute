# Use an official OpenJDK 23 runtime as a parent image
FROM eclipse-temurin:23-jre

# Set the working directory inside the container
WORKDIR /app

# Copy the built jar file
COPY target/spring-kafka-demo-0.0.1-SNAPSHOT.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
