FROM ubuntu:latest
LABEL authors="junhyunjee"

# BUILD
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

COPY . .

# Gradle build
RUN ./gradlew clean build -x test

# RUN
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]