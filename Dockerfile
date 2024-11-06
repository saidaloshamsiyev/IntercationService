FROM openjdk:17-jdk-alpine

EXPOSE 8089

COPY build/libs/IntercationService-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar","/app.jar"]