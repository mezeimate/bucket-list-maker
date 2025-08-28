COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw clean install
COPY src ./src
CMD ["./mvnw", "spring-boot:run"]