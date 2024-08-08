FROM openjdk:17
COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .
RUN ./mvnw compile
COPY src ./src
CMD ["./mvnw", "spring-boot:run"]
