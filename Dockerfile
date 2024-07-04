# Stage 1: Build the applications
FROM maven:3.8.3-openjdk-17-slim AS builder
# Set the working directory in the container
WORKDIR /app

# Copy the source code for both applications
COPY . .

RUN mvn clean package -DskipTests