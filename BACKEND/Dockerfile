# Build stage
FROM openjdk:21 AS build
WORKDIR /app
COPY . .
RUN ./gradlew build -x test

# Run stage
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.war drcomputer.war
EXPOSE 8080
ENTRYPOINT ["java","-jar","drcomputer.war"]