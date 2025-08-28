FROM eclipse-temurin:17-jdk-focal
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
COPY src ./src
RUN ./mvnw clean package -DskipTests
CMD ["java", "-Xms64m", "-Xmx256m", "-XX:MaxMetaspaceSize=64m", "-XX:+UseContainerSupport", "-jar", "target/bucketListMaker.jar"]
