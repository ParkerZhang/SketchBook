
FROM openjdk:17-jdk-alpine

#RUN apt-get update
#RUN apt-get install -y curl

VOLUME /tmp
COPY target/SpringShutdown-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
