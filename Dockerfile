# Build stage
FROM eclipse-temurin:17-jdk-focal AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests
COPY src ./src
RUN ./mvnw package -DskipTests

# Run stage
FROM eclipse-temurin:17-jdk-focal
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
# JVM memória korlátozása: 64-256 MB
CMD ["java", "-Xms64m", "-Xmx256m", "-jar", "app.jar"]