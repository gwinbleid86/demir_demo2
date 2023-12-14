FROM maven:3.9.5-amazoncorretto-17 AS build
WORKDIR /build
COPY src ./src
COPY pom.xml ./
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /build/target/minimalHttpServer*.jar  ./first_app.jar
EXPOSE 9091
CMD ["java", "-jar", "first_app.jar"]
