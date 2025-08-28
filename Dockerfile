FROM eclipse-temurin:17-jdk-focal
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
COPY src ./src
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-Xms64m -Xmx256m -XX:MaxMetaspaceSize=64m -XX:+UseContainerSupport"]
