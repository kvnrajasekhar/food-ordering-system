FROM maven:3.9.3-eclipse-temurin-20 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src


RUN mvn clean package -DskipTests

FROM openjdk:20-jdk-slim
WORKDIR /app
COPY --from=build /app/target/server-main-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/server-main-0.0.1-SNAPSHOT.jar"]
