# Use a base image with Java 11 pre-installed
FROM openjdk:17-jdk-slim

# Set the working directory to /app
WORKDIR /app

# Copy the application's JAR file to the container
ARG JAR_FILE
COPY ${JAR_FILE} /app/my-java-app.jar

# Expose port 8080 for the application
EXPOSE 8080

# Set the command to run when the container starts
CMD ["java", "-jar", "my-java-app.jar"]
