FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/entsoe-price-analyzer-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "/app/app.jar"]