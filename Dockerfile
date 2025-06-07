# Etapa 1: construir el JAR con Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: ejecutar el JAR con Java

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Comando para ejecutar tu app
ENTRYPOINT ["java", "-jar", "app.jar"]
