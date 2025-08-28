FROM eclipse-temurin:17-jdk-focal-slim
WORKDIR /app
COPY target/bucketListMaker-0.0.2-SNAPSHOT.jar app.jar
CMD ["java", "-Xms64m", "-Xmx256m", "-XX:MaxMetaspaceSize=64m", "-XX:+UseContainerSupport", "-jar", "app.jar"]
