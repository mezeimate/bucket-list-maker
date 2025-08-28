# Build stage
FROM eclipse-temurin:17-jdk-focal AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw

# Forráskód másolása és JAR build
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jdk-focal
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# JVM memória korlátozása: 64-256 MB, Metaspace limit
CMD ["java", "-Xms64m", "-Xmx256m", "-XX:MaxMetaspaceSize=64m", "-XX:+UseContainerSupport", "-jar", "app.jar"]