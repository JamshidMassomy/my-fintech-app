# Dockerfile
FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY fintech-1.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
