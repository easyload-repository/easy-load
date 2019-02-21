FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} easyload.jar
ENTRYPOINT  ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/easyload.jar"]