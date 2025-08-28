COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw clean install
COPY src ./src
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-Xms64m -Xmx256m -XX:MaxMetaspaceSize=32m -XX:+UseContainerSupport"]