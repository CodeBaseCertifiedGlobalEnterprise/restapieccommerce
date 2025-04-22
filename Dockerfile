# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy everything to /app
COPY . .

# Build the application
RUN ./mvnw clean install

# Run the jar file
CMD ["java", "-jar", "target/sampleProjectRestApi-0.0.1-SNAPSHOT.jar"]
