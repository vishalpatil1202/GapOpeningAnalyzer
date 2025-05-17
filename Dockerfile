# First stage: Build the application
FROM maven:3.9.6-eclipse-temurin-17 as builder

WORKDIR /app

# Copy all project files
COPY . .

# Package the app, skipping tests
RUN mvn clean package -DskipTests

# Second stage: Run the application
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy JAR from the builder stage
COPY --from=builder /app/target/GapOpeningAnalyzer-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
