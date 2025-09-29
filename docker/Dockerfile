FROM gradle:8.5.0-jdk21 as builder

WORKDIR /app

COPY . .

RUN gradle clean build

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/order-management-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]