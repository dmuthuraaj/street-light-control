# Stage 2: Runtime for iot-service
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app/device-service

# Copy the built JAR for iot-service
COPY --from=street-light-control-builder /app/device-service/target/app.jar .

# Set the active Spring profile (if needed)
ENV SPRING_PROFILES_ACTIVE=dev

# Expose the port for iot-service
EXPOSE 9090

# Run the iot-service application
ENTRYPOINT ["java", "-jar", "app.jar"]