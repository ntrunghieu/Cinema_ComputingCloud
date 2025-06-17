# Build stage
FROM openjdk:17-slim AS build
WORKDIR /app
COPY BACKEND .
RUN apt-get update && apt-get install -y findutils
RUN chmod +x gradlew
RUN ./gradlew build -x test


# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
