FROM openjdk:8-jdk-alpine
COPY target/*.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
