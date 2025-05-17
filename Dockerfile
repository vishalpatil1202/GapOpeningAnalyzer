# Use a lightweight OpenJDK 17 image
FROM eclipse-temurin:17-jdk-alpine

# Create app directory
WORKDIR /app

# Copy the packaged JAR file
COPY target/GapOpeningAnalyzer-0.0.1-SNAPSHOT.jar app.jar

# Expose port (optional)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]