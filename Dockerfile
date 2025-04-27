# First stage: Build the app
FROM openjdk:17-jdk-slim AS build

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Make mvnw executable
RUN chmod +x ./mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy the source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Second stage: Run the app
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy only the built jar from the build stage
COPY --from=build /app/target/sampleProjectRestApi-0.0.1-SNAPSHOT.jar app.jar

# Run the jar file
CMD ["java", "-jar", "app.jar"]
