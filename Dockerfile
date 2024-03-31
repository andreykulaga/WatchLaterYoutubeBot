FROM eclipse-temurin:21-alpine
LABEL authors="andreykulaga"
VOLUME /tmp
COPY target/WatchLaterYoutubeBot-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]